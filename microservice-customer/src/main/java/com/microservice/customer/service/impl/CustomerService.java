package com.microservice.customer.service.impl;

import com.microservice.customer.domain.Customer;
import com.microservice.customer.dto.CustomerRequestDTO;
import com.microservice.customer.dto.CustomerResponseDTO;
import com.microservice.customer.exception.ApiErrorEnum;
import com.microservice.customer.exception.CustomerException;
import com.microservice.customer.repository.ICustomerRepository;
import com.microservice.customer.service.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService implements ICustomerService {

    static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    ICustomerRepository customerRepository;

    ConversionService conversionService;

    @Override
    public Mono<CustomerResponseDTO> getById(UUID id) {
        return customerRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(
                                new CustomerException(
                                        ApiErrorEnum.CUSTOMER_NOT_FOUND)
                        )
                )
                .mapNotNull(customer -> conversionService.convert(customer, CustomerResponseDTO.class));
    }

    @Override
    public Mono<CustomerResponseDTO> save(CustomerRequestDTO customerDTO) {
        if (Objects.isNull(customerDTO)) {
            LOGGER.debug("Attempted to save a null CustomerDto.");
            throw new CustomerException(ApiErrorEnum.CUSTOMER_NOT_FOUND);
        }

        Customer customer = conversionService.convert(customerDTO, Customer.class);

        if (customer == null) {
            LOGGER.error("Conversion from CustomerDto to Customer entity failed.");
            throw new CustomerException(ApiErrorEnum.CUSTOMER_CONVERSION_FAILED);
        }

        String rawCode = UUID.randomUUID().toString();
        String encryptedCode = BCrypt.hashpw(rawCode, BCrypt.gensalt());

        customer.setEncryptedCode(encryptedCode);

        Mono<Customer> result = customerRepository.save(customer);

        return result.mapNotNull(c -> conversionService.convert(c, CustomerResponseDTO.class));
    }

    @Override
    public Mono<CustomerResponseDTO> update(UUID id, CustomerRequestDTO customerDTO) {
        if (!customerRepository.existsById(id).block()) {
            LOGGER.debug("Customer with ID {} not found. Cannot update.", id);
            throw new CustomerException(ApiErrorEnum.CUSTOMER_NOT_FOUND);
        }

        Customer customer = conversionService.convert(customerDTO, Customer.class);

        Mono<Customer> customerUpdated = customerRepository.save(Objects.requireNonNull(customer));

        return customerUpdated.mapNotNull(c -> conversionService.convert(c, CustomerResponseDTO.class));
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        if (!customerRepository.existsById(id).block()) {
            LOGGER.debug("Customer with ID {} not found. Cannot delete.", id);
            throw new CustomerException(ApiErrorEnum.CUSTOMER_NOT_FOUND);
        }

        customerRepository.deleteById(id);
        return Mono.empty();
    }

    @Override
    public Flux<CustomerResponseDTO> getCustomers() {
        Flux<Customer> customers = customerRepository.findAll();
        return customers.mapNotNull(customer -> conversionService.convert(customer, CustomerResponseDTO.class));
    }

}
