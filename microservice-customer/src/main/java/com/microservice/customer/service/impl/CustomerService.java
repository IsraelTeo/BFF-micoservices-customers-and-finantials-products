package com.microservice.customer.service.impl;

import com.microservice.customer.domain.Customer;
import com.microservice.customer.dto.CustomerDTO;
import com.microservice.customer.exception.ApiErrorEnum;
import com.microservice.customer.exception.CustomerException;
import com.microservice.customer.repository.ICustomerRepository;
import com.microservice.customer.service.ICustomerService;
import com.microservice.customer.util.code.EncryptedCode;
import com.microservice.customer.util.code.Encryptor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService implements ICustomerService {

    static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    ICustomerRepository customerRepository;

    ConversionService conversionService;

    @Override
    public Mono<CustomerDTO> getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(
                                new CustomerException(ApiErrorEnum.CUSTOMER_NOT_FOUND)
                        )
                )
                .mapNotNull(customer -> conversionService.convert(customer, CustomerDTO.class));
    }

    @Override
    public Mono<CustomerDTO> getCustomerByEncryptedCode(String encryptedCodeSent) {
        Mono<Customer> customerFind = customerRepository.findCustomerByEncryptedCode(encryptedCodeSent);
        return customerFind.switchIfEmpty(
                        Mono.error(new CustomerException(ApiErrorEnum.CUSTOMER_NOT_FOUND))
                )
                .mapNotNull(customer -> conversionService.convert(customer, CustomerDTO.class));
    }

    @Override
    public Mono<CustomerDTO> save(CustomerDTO customerDTO) {
        if (Objects.isNull(customerDTO)) {
            LOGGER.debug("Attempted to save a null CustomerDto.");
            throw new CustomerException(ApiErrorEnum.CUSTOMER_NOT_FOUND);
        }

        Customer customer = conversionService.convert(customerDTO, Customer.class);

        if (customer == null) {
            LOGGER.error("Conversion from CustomerDto to Customer entity failed.");
            throw new CustomerException(ApiErrorEnum.CUSTOMER_CONVERSION_FAILED);
        }

        customer.setCreationDate(LocalDate.now());

        String rawCode = EncryptedCode.generateRandomCode(8);
        String encryptedCode = Encryptor.encrypt(rawCode);

        customer.setEncryptedCode(encryptedCode);

        Mono<Customer> result = customerRepository.save(customer);

        return result.mapNotNull(c -> conversionService.convert(c, CustomerDTO.class));
    }

    @Override
    public Mono<CustomerDTO> update(UUID id, CustomerDTO customerDTO) {
        if (!customerRepository.existsById(id).block()) {
            LOGGER.debug("Customer with ID {} not found. Cannot update.", id);
            throw new CustomerException(ApiErrorEnum.CUSTOMER_NOT_FOUND);
        }

        Customer customer = conversionService.convert(customerDTO, Customer.class);

        Mono<Customer> customerUpdated = customerRepository.save(Objects.requireNonNull(customer));

        return customerUpdated.mapNotNull(c -> conversionService.convert(c, CustomerDTO.class));
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
    public Flux<CustomerDTO> getCustomers() {
        Flux<Customer> customers = customerRepository.findAll();
        return customers.mapNotNull(customer -> conversionService.convert(customer, CustomerDTO.class));
    }

    /*public Mono<Boolean> validateCode(String encryptedCodeSent, UUID customerId) {
        LOGGER.info("Validating code for customer with ID: {}", customerId);

        return customerRepository.findEncryptedCodeByCustomerId(customerId)
                .flatMap(
                        encryptedCodeFromDb -> {
                            LOGGER.debug("Encrypted code from DB: {}", encryptedCodeFromDb);
                            LOGGER.debug("Encrypted code sent: {}", encryptedCodeSent);
                            try {
                                LOGGER.debug("Decrypting code from database.");
                                String decryptedDbCode = Encryptor.decrypt(encryptedCodeFromDb);

                                LOGGER.debug("Decrypting code from request.");
                                String decryptedSentCode = Encryptor.decrypt(encryptedCodeSent);

                                boolean isValid = decryptedDbCode.equals(decryptedSentCode);
                                LOGGER.info("Code validation result for customer {}: {}", customerId, isValid);

                                return Mono.just(isValid);
                            } catch (Exception e) {
                                LOGGER.error("Error during code validation for customer {}: {}", customerId, e.getMessage(), e);
                                return Mono.just(false);
                            }
                        })
                .switchIfEmpty(
                        Mono.fromCallable(() -> {
                            LOGGER.warn("No encrypted code found for customer with ID: {}", customerId);
                            return false;
                        })
                );
    }*/

}
