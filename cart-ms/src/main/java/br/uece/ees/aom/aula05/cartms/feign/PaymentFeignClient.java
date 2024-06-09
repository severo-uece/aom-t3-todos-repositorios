package br.uece.ees.aom.aula05.cartms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.uece.ees.aom.aula05.cartms.dto.BarcodePaymentInfo;
import br.uece.ees.aom.aula05.cartms.dto.CreditCardPaymentInfo;
import br.uece.ees.aom.aula05.cartms.dto.PaymentResult;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "payment-ms")
public interface PaymentFeignClient {

    @PostMapping("/pay/credit-card")
    @CircuitBreaker(name = "payment-ms", fallbackMethod = "bokenCreditCardPaymentFallback")
    public PaymentResult creditCardPayment(@RequestBody CreditCardPaymentInfo info);

    @PostMapping("/pay/barcode")
    @CircuitBreaker(name = "payment-ms", fallbackMethod = "bokenBarcodePaymentFallback")
    public PaymentResult barcodePayment(@RequestBody BarcodePaymentInfo info);

    default PaymentResult bokenCreditCardPaymentFallback(CreditCardPaymentInfo info, Throwable e) {
        return new PaymentResult(-1L, info.getCustomerId(), info.getValue(), "SERVIÇO INDISPONÍVEL - CREDITO", null);
    }

    default PaymentResult bokenBarcodePaymentFallback(BarcodePaymentInfo info, Throwable e) {
         return new PaymentResult(-1L, info.getCustomerId(), info.getValue(), "SERVIÇO INDISPONÍVEL - BOLETO", null);
    }

}