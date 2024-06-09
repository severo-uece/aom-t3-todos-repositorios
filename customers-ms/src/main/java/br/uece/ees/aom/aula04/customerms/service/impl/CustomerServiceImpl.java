package br.uece.ees.aom.aula04.customerms.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.uece.ees.aom.aula04.customerms.model.dto.CreateCustomerDto;
import br.uece.ees.aom.aula04.customerms.model.dto.CustomerDto;
import br.uece.ees.aom.aula04.customerms.model.dto.UpdateCustomerDto;
import br.uece.ees.aom.aula04.customerms.model.entity.Customer;
import br.uece.ees.aom.aula04.customerms.model.repository.CustomerRepository;
import br.uece.ees.aom.aula04.customerms.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static ModelMapper mm = new ModelMapper();

	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDto> findAll() {

		List<Customer> customers = customerRepository.findAll();
		List<CustomerDto> customersDto = Arrays.asList(mm.map(customers, CustomerDto[].class));

		return customersDto;
	}

	@Override
	public CustomerDto createCustomer(CreateCustomerDto createCustomer) {

		Customer customer = new Customer(null,
				createCustomer.getName(),
				createCustomer.getAge(),
				createCustomer.getEmail(),
				Boolean.TRUE);

		// ---- SUBSTITUIR PELO USO DO MODELMAPPER ----
		// Customer customer = mm.map(createCustomer, Customer.class);

		// ---- Gravando no banco de dados ----
		customerRepository.save(customer);

		// ---- SUBSTITUIRPELO USO DO MODELMAPPER ----
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setName(customer.getName());
		customerDto.setAge(customer.getAge());
		customerDto.setEmail(customer.getEmail());
		customerDto.setActive(customer.getActive());

		// CustomerDto customerDto = mm.map(customer, CustomerDto.class);

		return customerDto;
	}

	@Override
	public CustomerDto findCustomerById(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found."));
		return mm.map(customer, CustomerDto.class);
	}

	@Override
	public CustomerDto findCustomerByEmail(String email) {
		Customer customer = customerRepository.findByEmail(email);
		return mm.map(customer, CustomerDto.class);
	}

	@Override
	public CustomerDto updateCustomer(Long id, UpdateCustomerDto updateCustomer) {
		Customer customer = customerRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found."));

		customer.setName(updateCustomer.getName());
		customer.setAge(updateCustomer.getAge());
		customer.setEmail(updateCustomer.getEmail());
		customerRepository.save(customer);

		return mm.map(customer, CustomerDto.class);
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found."));

		customerRepository.delete(customer);
		return;
	}

	@Override
	public CustomerDto findCustomerByName(String name) {
		Customer customer = customerRepository.findByName(name);
		return mm.map(customer, CustomerDto.class);
	}

}
