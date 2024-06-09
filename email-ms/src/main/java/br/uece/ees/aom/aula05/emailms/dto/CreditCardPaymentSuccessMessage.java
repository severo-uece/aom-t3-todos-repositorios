package br.uece.ees.aom.aula05.emailms.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CreditCardPaymentSuccessMessage {

    private String customerName;
    private String customerEmail;
    private Double value;
    private Integer installments;

}
