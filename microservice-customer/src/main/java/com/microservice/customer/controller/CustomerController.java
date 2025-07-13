package com.microservice.customer.controller;

import com.microservice.customer.dto.CustomerDTO;
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
    public Flux<CustomerDTO> getCustomers() {
        LOGGER.info("Obtain all the customers");
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Mono<CustomerDTO> getCustomerById(@Min(1) @PathVariable("id") UUID id) {
        LOGGER.info("Obtain information from a customer with {}", id);
        return customerService.getCustomerById(id);
    }

    @GetMapping("/code/{code}")
    public Mono<ResponseEntity<CustomerDTO>> getCustomerByCode(@PathVariable String code) {
        LOGGER.info("Searching for customer with code {}", code);
        return customerService.getCustomerByEncryptedCode(code)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<CustomerDTO>> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.info("Saving new customer");
        return customerService.save(customerDTO)
                .map(savedCustomer -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(savedCustomer));
    }

    @PutMapping("/{id}")
    public Mono<CustomerDTO> updateCustomer(@Min(1) @PathVariable("id") UUID id, @RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.info("Updating a customer with {}", id);
        return customerService.update(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@Min(1) @PathVariable UUID id) {
        LOGGER.info("Deleting a customer with {}", id);
        return customerService.deleteById(id);
    }

}
