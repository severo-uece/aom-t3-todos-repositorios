package br.uece.ees.aom.aula05.firebasems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CreditCardPaymentSuccessAppNotificationMessage {

    private Long customerId;
    private String customerName;
    private Double value;
    private Integer installments;

}
