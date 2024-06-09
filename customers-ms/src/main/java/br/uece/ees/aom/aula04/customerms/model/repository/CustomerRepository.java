package br.uece.ees.aom.aula04.customerms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.uece.ees.aom.aula04.customerms.model.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	@Override
	@SuppressWarnings("null")
    List<Customer> findAll();
	
	//	Query automática gerada pela sintaxe da JPA
	//	Ver mais em https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
	Customer findByEmail(String email);
	
	Customer findByName(String name);

	//	Query JPQL com parâmetros posicionais
	@Query("select c from Customer c where c.email like ?1")
	List<Customer> findByEmailPositionParam(String email);

	//  Query JPQL com parâmetros nomeados
	@Query("select c from Customer c where c.email like :email")	
	Customer findByEmailNamedParam(@Param("email") String email);

	//  Query Nativa
	@Query(value = "SELECT * FROM tb_customer WHERE ds_email LIKE :email LIMIT 1", nativeQuery = true)
	Customer findByEmailNative(@Param("email") String email);

}
