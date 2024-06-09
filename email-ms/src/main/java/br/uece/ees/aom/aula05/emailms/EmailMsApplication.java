package br.uece.ees.aom.aula05.emailms;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class EmailMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailMsApplication.class, args);
	}

}
