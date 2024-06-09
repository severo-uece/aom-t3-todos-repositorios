package br.uece.ees.aom.aula05.cartms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BarcodePaymentInfo {

    private Long customerId;
    private String customerName;
    private String customerEmail;
    private Double value;
    private String barcode;

}
