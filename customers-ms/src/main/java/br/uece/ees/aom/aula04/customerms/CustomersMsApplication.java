package br.uece.ees.aom.aula04.customerms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;

@Configuration
@EnableDiscoveryClient
@SpringBootApplication
public class CustomersMsApplication {

	@Bean
	public OtlpGrpcSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
		return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomersMsApplication.class, args);
	}

}
