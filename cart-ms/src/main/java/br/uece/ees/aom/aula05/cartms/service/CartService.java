package br.uece.ees.aom.aula05.cartms.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.uece.ees.aom.aula05.cartms.dto.BarcodePaymentInfo;
import br.uece.ees.aom.aula05.cartms.dto.CreditCardInfoDto;
import br.uece.ees.aom.aula05.cartms.dto.CreditCardPaymentInfo;
import br.uece.ees.aom.aula05.cartms.dto.CustomerDto;
import br.uece.ees.aom.aula05.cartms.dto.PaymentResult;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class CartService {

    // private PaymentFeignClient paymentClient;
    // private CustomerFeignClient customerClient;
    private RestTemplate restTemplate;
    private final MeterRegistry meterRegistry;
    private Counter falhaPagamentoCounter;

    public CartService(
        // PaymentFeignClient paymentClient, CustomerFeignClient customerClient,
            RestTemplate restTemplate, MeterRegistry meterRegistry) {
        // this.paymentClient = paymentClient;
        // this.customerClient = customerClient;
        this.restTemplate = restTemplate;
        this.meterRegistry = meterRegistry;
        
        falhaPagamentoCounter = Counter.builder("cart_falha_pagamento_carrinho")
            .description("Quantidade de falhas no pagamento de carrinho de compras")
            .register(this.meterRegistry);

    }

    public String generateBarCode(Double value) {
        return String.format("01012345000%1$,.2f-UECE-AOM", value);
    }

    public ResponseEntity<PaymentResult> payCart(String method, Double value, Long customerId) throws Exception {

        // Requisição com Feign :: não suporta tracing com OPTL e Jaeger
        // CustomerDto customer = customerClient.getCustomerInfo(customerId);

        String url = String.format("%s/customers/%d", "http://localhost:8090/customers-ms", customerId);
        CustomerDto customer = null;
        
        try {
            customer = restTemplate.getForObject(url, CustomerDto.class);
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentResult("Cliente não encontrado"));
            }
        } catch (RestClientException e) {
            falhaPagamentoCounter.increment();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PaymentResult(e.getMessage()));
        }

        try {

            switch (method) {
                case "CREDIT" -> {
                    url = String.format("%s/customers/%d/credit-card", "http://localhost:8090/customers-ms", customerId);
                    CreditCardInfoDto ccInfo = null;
                    
                    try {
                        
                        // Requisição com Feign :: não suporta tracing com OPTL e Jaeger
                        // CreditCardInfoDto ccInfo = customerClient.getCustomerCreditCard(customerId);
                        
                        ccInfo = restTemplate.getForObject(url, CreditCardInfoDto.class);
                        if (ccInfo == null) {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentResult("Cliente não possui cartão cadastrado."));
                        }
                    } catch (RestClientException e) {
                        falhaPagamentoCounter.increment();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PaymentResult(e.getMessage()));
                    }

                    CreditCardPaymentInfo ccpi = new CreditCardPaymentInfo();
                    ccpi.setCustomerId(customer.getId());
                    ccpi.setCustomerName(customer.getName());
                    ccpi.setCustomerEmail(customer.getEmail());
                    ccpi.setCardNumber(ccInfo.getCardNumber());
                    ccpi.setCardCCV(ccInfo.getCardCCV());
                    ccpi.setValue(value);
                    ccpi.setInstallments(1);

                    // Requisição com Feign :: não suporta tracing com OPTL e Jaeger
                    // return paymentClient.creditCardPayment(ccpi);

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Type", "application/json");

                    HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(ccpi), headers);
                    url = String.format("%s/pay/credit-card", "http://localhost:8090/payment-ms");

                    PaymentResult pr = restTemplate.postForObject(url, entity, PaymentResult.class);
                    return ResponseEntity.status(HttpStatus.OK).body(pr);
                }
                case "BARCODE" -> {

                    String barcode = generateBarCode(value);
                    BarcodePaymentInfo bcpi = new BarcodePaymentInfo();
                    bcpi.setValue(value);
                    bcpi.setCustomerId(customer.getId());
                    bcpi.setCustomerName(customer.getName());
                    bcpi.setCustomerEmail(customer.getEmail());
                    bcpi.setBarcode(barcode);

                    // Requisição com Feign :: não suporta tracing com OPTL e Jaeger
                    // return paymentClient.barcodePayment(bcpi);

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Type", "application/json");

                    HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(bcpi), headers);
                    url = String.format("%s/pay/barcode", "http://localhost:8090/payment-ms");

                    PaymentResult pr = restTemplate.postForObject(url, entity, PaymentResult.class);
                    return ResponseEntity.status(HttpStatus.OK).body(pr);
                }
                default -> throw new IllegalArgumentException();
            }
        } catch (RestClientException e) {
            falhaPagamentoCounter.increment();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PaymentResult(e.getMessage()));
        }
    }

}
