package br.uece.ees.aom.aula05.firebasems;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class FirebaseMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirebaseMsApplication.class, args);
	}

}
