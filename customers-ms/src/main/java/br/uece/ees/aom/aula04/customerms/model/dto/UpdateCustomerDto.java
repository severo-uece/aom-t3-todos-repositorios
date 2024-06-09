package br.uece.ees.aom.aula04.customerms.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateCustomerDto {

	@JsonProperty("nome")
	private String name;
	@JsonProperty("idade")
	private Integer age;
	@JsonProperty("email")
	private String email;

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

}
