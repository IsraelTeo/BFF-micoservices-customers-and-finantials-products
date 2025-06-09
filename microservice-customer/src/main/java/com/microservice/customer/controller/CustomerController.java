package com.microservice.customer.controller;

import com.microservice.customer.dto.CustomerRequestDTO;
import com.microservice.customer.dto.CustomerResponseDTO;
import com.microservice.customer.service.impl.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/customers")
public class CustomerController {

    static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    CustomerService customerService;

    @GetMapping
    public Flux<CustomerResponseDTO> getCustomers() {
        LOGGER.info("Obtain all the customers");
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Mono<CustomerResponseDTO> getCustomerById(@Min(1) @PathVariable("id") UUID id) {
        LOGGER.info("Obtain information from a customer with {}", id);
        return customerService.getById(id);
    }

    @PostMapping
    public Mono<CustomerResponseDTO> saveCustomer(@RequestBody @Valid CustomerRequestDTO customerDTO) {
        LOGGER.info("Saving new customer");
        return customerService.save(customerDTO);
    }

    @PutMapping("/{id}")
    public Mono<CustomerResponseDTO> updateCustomer(@Min(1) @PathVariable("id") UUID id, @RequestBody @Valid CustomerRequestDTO customerDTO) {
        LOGGER.info("Updating a customer with {}", id);
        return customerService.update(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@Min(1) @PathVariable UUID id) {
        LOGGER.info("Deleting a customer with {}", id);
        return customerService.deleteById(id);
    }

}
