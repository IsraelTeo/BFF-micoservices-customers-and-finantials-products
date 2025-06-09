package com.microservice.customer.service;

import com.microservice.customer.dto.CustomerRequestDTO;
import com.microservice.customer.dto.CustomerResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ICustomerService {

    Mono<CustomerResponseDTO> getById(UUID id);

    Mono<CustomerResponseDTO> save (CustomerRequestDTO customerDTO);

    Mono<CustomerResponseDTO> update (UUID id, CustomerRequestDTO customerDTO);

    Mono<Void> deleteById(UUID id);

    Flux<CustomerResponseDTO> getCustomers();
}
