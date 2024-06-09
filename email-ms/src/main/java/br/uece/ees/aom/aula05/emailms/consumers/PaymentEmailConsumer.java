package br.uece.ees.aom.aula05.emailms.consumers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.uece.ees.aom.aula05.emailms.dto.BarcodePaymentEmailMessage;
import br.uece.ees.aom.aula05.emailms.dto.CreditCardPaymentSuccessMessage;

@Component
public class PaymentEmailConsumer {

    private ObjectMapper objectMapper;

    public PaymentEmailConsumer(){
        objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = { "barcode-dispatch-email-queue" })
    public void sendPendingBarcodePaymentEmail(@Payload Message message) {

        try {
            BarcodePaymentEmailMessage info = objectMapper.readValue(message.getBody(), BarcodePaymentEmailMessage.class);
            System.out.println(String.format("Enviando email de boleto pendente para %s", info.getCustomerEmail()));
        } catch (Exception e) {
            System.err.println("Falha ao tratar mensagem. ");
            System.err.println(message.getBody());
        }
    }

    @RabbitListener(queues = { "success-payment-email-queue" })
    public void sendSuccessPaymentEmail(@Payload Message message) {

        try {
            CreditCardPaymentSuccessMessage info = objectMapper.readValue(message.getBody(), CreditCardPaymentSuccessMessage.class);
            System.out.println(String.format("Enviando email de sucesso de pagamento para %s", info.getCustomerEmail()));
        } catch (Exception e) {
            System.err.println("Falha ao tratar mensagem. ");
            System.err.println(message.getBody());
        }
    }

}
