package br.uece.ees.aom.aula05.paymentms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PaymentResult {

    private Long paymentId;
    private Long customerId;
    private Double pyamentValue;
    private String paymentStatus;
    
}
