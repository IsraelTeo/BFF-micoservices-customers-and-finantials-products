package com.microservice.product.service;

import com.microservice.product.dto.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IProductService {

    Mono<ProductDTO> getProductById(UUID id);

    Mono<ProductDTO> getProductByEncryptedCode(String encryptedCodeSent);

    Flux<ProductDTO> getProductsByCustomerByCode (String customerCode);

    Mono<ProductDTO> save (ProductDTO productDTO);

    Mono<ProductDTO> update (UUID id, ProductDTO productDTO);

    Mono<Void> deleteById(UUID id);

    Flux<ProductDTO> getProducts();
}
