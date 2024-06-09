package br.uece.ees.aom.aula05.cartms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.uece.ees.aom.aula05.cartms.dto.PaymentResult;
import br.uece.ees.aom.aula05.cartms.service.CartService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotAuthorizedException;

@RestController()
@EnableFeignClients
public class CartController {

    private CartService cartService;

    @Value("${spring.application.name}")
    private String applicationName;
    
    private final MeterRegistry meterRegistry;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private Counter tentativaPagamentoCounter;

    public CartController(CartService cartService, MeterRegistry meterRegistry){
        this.cartService = cartService;
        this.meterRegistry = meterRegistry;

        tentativaPagamentoCounter = Counter.builder("cart_tentativas_pagamento_carrinho")
            .description("Quantidade de tentativas de pagamento de carrinho de compras")
            .register(this.meterRegistry);
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResult> startPayment(@RequestParam String method, @RequestParam Double value, @RequestParam Long customerId) throws Exception {

        tentativaPagamentoCounter.increment();
        logger.info("Requisição recebida em {} no endpoint /pay ", applicationName);

        try {
            
            return cartService.payCart(method, value, customerId);
            
        } catch (Exception e) {
            PaymentResult pr = new PaymentResult();
            pr.setPaymentStatus("ERRO");
            pr.setErrorDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pr);
        }
    }
    
}
