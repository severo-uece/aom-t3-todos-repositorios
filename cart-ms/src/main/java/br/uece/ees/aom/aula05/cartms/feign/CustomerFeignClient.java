package br.uece.ees.aom.aula05.cartms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.uece.ees.aom.aula05.cartms.dto.CreditCardInfoDto;
import br.uece.ees.aom.aula05.cartms.dto.CustomerDto;

@FeignClient(name = "customers-ms")
public interface CustomerFeignClient {

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomerInfo(@PathVariable("id") Long customerId);

    @GetMapping("/customers/{id}/credit-card")
    public CreditCardInfoDto getCustomerCreditCard(@PathVariable("id") Long customerId);

}