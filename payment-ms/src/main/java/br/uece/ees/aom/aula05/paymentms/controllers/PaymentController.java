package br.uece.ees.aom.aula05.paymentms.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.uece.ees.aom.aula05.paymentms.dto.BarcodePaymentInfo;
import br.uece.ees.aom.aula05.paymentms.dto.CreditCardPaymentInfo;
import br.uece.ees.aom.aula05.paymentms.dto.PaymentResult;
import br.uece.ees.aom.aula05.paymentms.service.PaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController()
@RequestMapping("/pay")
public class PaymentController {

    private PaymentService paymentService;
    private Environment env;

    @Value("${spring.application.name}")
    private String applicationName;

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(Environment env, PaymentService paymentService){
        this.env = env;
        this.paymentService = paymentService;
    }
    

    @PostMapping("/credit-card")
    public ResponseEntity<PaymentResult> creditCardPayment(@RequestBody CreditCardPaymentInfo info) throws Exception {

        logger.info("Requisição recebida em {} no endpoint /credit-card ", applicationName);
        return paymentService.payWithCreditCard(info);
    }

      
    @PostMapping("/barcode")
    public ResponseEntity<PaymentResult> barcodePayment(@RequestBody BarcodePaymentInfo info) throws Exception {

        logger.info("Requisição recebida em {} no endpoint /barcode ", applicationName);
        return paymentService.payWithBarcode(info);
    }
    
    @GetMapping("/env")
    public String getMethodName(@RequestParam(name = "name", required = false) String name) {
        return env.getProperty(name != null ? name : "eureka.instance.instance-id");
    }
    

}
