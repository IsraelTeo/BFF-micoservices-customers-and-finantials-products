package com.microservice.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableR2dbcRepositories
public class MicroserviceProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProductApplication.class, args);
	}

}
