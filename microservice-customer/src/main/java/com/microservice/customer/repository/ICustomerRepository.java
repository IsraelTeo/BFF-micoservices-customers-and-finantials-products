package com.microservice.customer.repository;

import com.microservice.customer.domain.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICustomerRepository extends R2dbcRepository<Customer, UUID> {

    Optional<Customer> findCustomerByEncryptedCode(String encryptedCode);

    boolean existsByEncryptedCode(String encryptedCode);
}
