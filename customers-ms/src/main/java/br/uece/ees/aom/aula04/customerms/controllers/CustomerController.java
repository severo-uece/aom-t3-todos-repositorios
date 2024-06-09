package br.uece.ees.aom.aula04.customerms.controllers;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.uece.ees.aom.aula04.customerms.model.dto.CreateCustomerDto;
import br.uece.ees.aom.aula04.customerms.model.dto.CustomerDto;
import br.uece.ees.aom.aula04.customerms.model.dto.UpdateCustomerDto;
import br.uece.ees.aom.aula04.customerms.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService customerService;

	@Value("${spring.application.name}")
    private String applicationName;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	public CustomerController(Environment env, CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping()
	public List<CustomerDto> findAll() {
		return customerService.findAll();
	}

	@GetMapping("/byEmail")
	public CustomerDto findByEmail(@RequestParam("email") String email) {
		return customerService.findCustomerByEmail(email);
	}

	@GetMapping("/{id}")
	public CustomerDto findById(@PathVariable("id") Long id) {
		logger.info("Requisição recebida em {} no endpoint /id ", applicationName);
		return customerService.findCustomerById(id);
	}

	// Uso de Record para não criar um novo DTO
	@GetMapping("/{id}/credit-card")
	public CreditCardDto findCreditCardById(@PathVariable("id") Long id) {
		logger.info("Requisição recebida em {} no endpoint /id/credit-card ", applicationName);

		// Simulando tempo de pagamento em uma plataforma de pagamentos
        Random random = new Random();
        double waitTime = 300 + (random.nextInt(2) * 1000);
        try {Thread.sleep((int) waitTime);} catch(Exception ignored) {}

		return new CreditCardDto("4007702835532454", "000");
	}

	@PostMapping()
	public CustomerDto create(@RequestBody CreateCustomerDto createCustomer) {
		return customerService.createCustomer(createCustomer);
	}

	@PutMapping("/{id}")
	public CustomerDto update(@PathVariable("id") Long id, @RequestBody UpdateCustomerDto updateCustomer) {
		return customerService.updateCustomer(id, updateCustomer);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		customerService.deleteCustomer(id);
		return;
	}

}

record CreditCardDto(String cardNumber, String cardCCV){}