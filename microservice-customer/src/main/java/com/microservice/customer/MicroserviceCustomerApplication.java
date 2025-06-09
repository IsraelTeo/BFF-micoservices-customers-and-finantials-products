package com.microservice.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableR2dbcRepositories
public class MicroserviceCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCustomerApplication.class, args);
	}

}
