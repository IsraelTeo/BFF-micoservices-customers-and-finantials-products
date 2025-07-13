package com.microservice.customer.service;

import com.microservice.customer.dto.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ICustomerService {

    Mono<CustomerDTO> getCustomerById(UUID id);

    Mono<CustomerDTO> getCustomerByEncryptedCode(String encryptedCodeSent);

    Mono<CustomerDTO> save (CustomerDTO customerDTO);

    Mono<CustomerDTO> update (UUID id, CustomerDTO customerDTO);

    Mono<Void> deleteById(UUID id);

    Flux<CustomerDTO> getCustomers();
}
