package com.microservice.BFF.service;

import com.microservice.BFF.connector.connections.CustomerConnector;
import com.microservice.BFF.connector.connections.ProductConnector;
import com.microservice.BFF.connector.payload.CustomerDTO;
import com.microservice.BFF.connector.payload.CustomerProductsDTO;
import com.microservice.BFF.connector.payload.ProductDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BFFService {

    static Logger LOGGER = LoggerFactory.getLogger(BFFService .class);

    CustomerConnector customerConnector;

    ProductConnector productConnector;

    public Mono<CustomerProductsDTO> getCustomerAndProducts(String customerCode) {
        Mono<CustomerDTO> customerMono = customerConnector.getCustomer(customerCode);
        Flux<ProductDTO> productsFlux = productConnector.getProductsByCustomerEncryptedCode(customerCode);

        return Mono.zip(customerMono, productsFlux.collectList())
                .map(tuple -> {
                    CustomerDTO customer = tuple.getT1();
                    List<ProductDTO> products = tuple.getT2();

                    return CustomerProductsDTO.builder()
                            .customerDTO(customer)
                            .products(products)
                            .build();
                });
    }
}
