package com.microservice.product.repository;

import com.microservice.product.domain.Product;
import com.microservice.product.dto.ProductDTO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<Product, UUID> {

    Mono<Product> findProductByEncryptedCode(String encryptedCode);

    Mono<String> findEncryptedCodeById(UUID id);

    Flux<Product> findProductsByCustomerCode(String customerCode);
}
