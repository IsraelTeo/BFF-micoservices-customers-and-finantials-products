package com.microservice.customer.repository;

import com.microservice.customer.domain.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ICustomerRepository extends R2dbcRepository<Customer, UUID> {

    Mono<Customer> findCustomerByEncryptedCode(String encryptedCode);

    Mono<String> findEncryptedCodeByCustomerId(UUID customerId);

}
