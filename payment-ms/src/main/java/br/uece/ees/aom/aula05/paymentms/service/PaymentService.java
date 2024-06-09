package br.uece.ees.aom.aula05.paymentms.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.uece.ees.aom.aula05.paymentms.dto.BarcodePaymentEmailMessage;
import br.uece.ees.aom.aula05.paymentms.dto.BarcodePaymentInfo;
import br.uece.ees.aom.aula05.paymentms.dto.CreditCardPaymentInfo;
import br.uece.ees.aom.aula05.paymentms.dto.CreditCardPaymentSuccessAppNotificationMessage;
import br.uece.ees.aom.aula05.paymentms.dto.CreditCardPaymentSuccessMessage;
import br.uece.ees.aom.aula05.paymentms.dto.PaymentResult;

@Component
public class PaymentService {
    
    private static Long PAYMENT_ID = 1L;
    private AmqpTemplate amqpTemplate;
    private ObjectMapper objectMapper;

    public PaymentService(AmqpTemplate amqpTemplate){
        this.amqpTemplate = amqpTemplate;
        objectMapper = new ObjectMapper();
    }

    public ResponseEntity<PaymentResult> payWithCreditCard(CreditCardPaymentInfo info) throws Exception {

        // Dados básicos da resposta
        PaymentResult result = new PaymentResult();
        result.setCustomerId(info.getCustomerId());
        result.setPaymentId(PAYMENT_ID++);
        result.setPyamentValue(info.getValue());

        // Simulação de regra de validação
        if(info.getValue() > 1000){
            result.setPaymentStatus("FALHA - VALOR ACIMA DO LIMITE PERMITIDO");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        // Simulando tempo de pagamento em uma plataforma de pagamentos
        double waitTime = info.getValue() * 5;
        try {Thread.sleep((int) waitTime);} catch(Exception ignored) {}

        result.setPaymentStatus("SUCESSO");
        dispatchPaymentFinishedEmailMessage(info);
        dispatchPaymentFinishedAppNotification(info);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<PaymentResult> payWithBarcode(BarcodePaymentInfo info) throws Exception {

        dispatchBarcodeEmailMessage(info);

        double waitTime = info.getValue() * 5;
        try {Thread.sleep((int) waitTime);} catch(Exception ignored) {}

        PaymentResult result = new PaymentResult();
        result.setCustomerId(info.getCustomerId());
        result.setPaymentId(PAYMENT_ID++);
        result.setPyamentValue(info.getValue());
        result.setPaymentStatus("AGUARDANDO PAGAMENTO");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private void dispatchPaymentFinishedEmailMessage(CreditCardPaymentInfo info) throws Exception {
        CreditCardPaymentSuccessMessage cspm = new CreditCardPaymentSuccessMessage();
        cspm.setCustomerName(info.getCustomerName());
        cspm.setCustomerEmail(info.getCustomerEmail());
        cspm.setValue(info.getValue());
        cspm.setInstallments(info.getInstallments());
        
        String exchange = "email-exchange";
        String routingKey = "notification.success.credit-card.email";
        amqpTemplate.convertAndSend(exchange, routingKey, objectMapper.writeValueAsString(cspm));
    }

    private void dispatchPaymentFinishedAppNotification(CreditCardPaymentInfo info) throws Exception {
        CreditCardPaymentSuccessAppNotificationMessage cspm = new CreditCardPaymentSuccessAppNotificationMessage();
        cspm.setCustomerId(info.getCustomerId());
        cspm.setCustomerName(info.getCustomerName());
        cspm.setValue(info.getValue());
        cspm.setInstallments(info.getInstallments());
        
        String exchange = "firebase-exchange";
        String routingKey = "notification.success.credit-card.push";
        amqpTemplate.convertAndSend(exchange, routingKey, objectMapper.writeValueAsString(cspm));
    }
    
    private void dispatchBarcodeEmailMessage(BarcodePaymentInfo info) throws Exception {
        BarcodePaymentEmailMessage bpem = new BarcodePaymentEmailMessage();
        bpem.setCustomerName(info.getCustomerName());
        bpem.setCustomerEmail(info.getCustomerEmail());
        bpem.setBarcode(info.getBarcode());
        bpem.setValue(info.getValue());
        
        String exchange = "email-exchange";
        String routingKey = "notification.pending.barcode.email";
        amqpTemplate.convertAndSend(exchange, routingKey, objectMapper.writeValueAsString(bpem));
    }
    
}
