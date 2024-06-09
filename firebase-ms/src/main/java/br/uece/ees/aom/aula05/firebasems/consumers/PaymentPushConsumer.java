package br.uece.ees.aom.aula05.firebasems.consumers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.uece.ees.aom.aula05.firebasems.dto.CreditCardPaymentSuccessAppNotificationMessage;

@Component
public class PaymentPushConsumer {
    
    private ObjectMapper objectMapper;

    public PaymentPushConsumer(){
        objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = { "success-payment-app-push-queue" })
    public void sendSuccessPaymentPushNotification(@Payload Message message) {

        try {
            CreditCardPaymentSuccessAppNotificationMessage info = 
                objectMapper.readValue(message.getBody(), CreditCardPaymentSuccessAppNotificationMessage.class);
            System.out.println(String.format("Enviando push notification de sucesso para %s (ID: %d)", info.getCustomerName(), info.getCustomerId()));
        } catch (Exception e) {
            System.err.println("Falha ao tratar mensagem. ");
            System.err.println(message.getBody());
        }
    }
}
