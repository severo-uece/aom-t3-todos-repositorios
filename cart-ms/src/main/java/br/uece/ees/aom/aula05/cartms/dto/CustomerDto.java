package br.uece.ees.aom.aula05.cartms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CustomerDto {

	private Long id;
	private String name;
	private String email;

}
