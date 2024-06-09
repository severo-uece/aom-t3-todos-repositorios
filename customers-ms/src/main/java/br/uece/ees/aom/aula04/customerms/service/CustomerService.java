package br.uece.ees.aom.aula04.customerms.service;

import java.util.List;

import br.uece.ees.aom.aula04.customerms.model.dto.CreateCustomerDto;
import br.uece.ees.aom.aula04.customerms.model.dto.CustomerDto;
import br.uece.ees.aom.aula04.customerms.model.dto.UpdateCustomerDto;

public interface CustomerService {
	
	public List<CustomerDto> findAll();
	public CustomerDto findCustomerById(Long id);
	public CustomerDto findCustomerByName(String name);
	public CustomerDto findCustomerByEmail(String email);
	public CustomerDto createCustomer(CreateCustomerDto createCustomer);
	public CustomerDto updateCustomer(Long id, UpdateCustomerDto updateCustomer);
	public void deleteCustomer(Long id);

}
