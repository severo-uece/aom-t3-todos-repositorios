package br.uece.ees.aom.aula04.customerms.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_customers")
public class Customer implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	
	public Customer(){}

	public Customer(Long id, String name, Integer age, String email, Boolean active){
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.active = active;
	}
	
	@Id
	@Column(name = "id_customer")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ds_name")
	private String name;

	@Column(name = "nm_age")
	private Integer age;

	@Column(name = "ds_email")
	private String email;

	@Column(name = "st_active")
	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	

}
