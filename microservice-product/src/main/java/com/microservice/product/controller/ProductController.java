package com.microservice.product.controller;

import com.microservice.product.dto.ProductDTO;
import com.microservice.product.service.IProductService;
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
@RequestMapping("/products")
public class ProductController {

    static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    IProductService productService;

    @GetMapping
    public Flux<ProductDTO> getProducts() {
        LOGGER.info("Obtain all the products");
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDTO> getProductById(@Min(1) @PathVariable UUID id) {
        LOGGER.info("Obtain information from a product with {}", id);
        return productService.getProductById(id);
    }

    @GetMapping("/code/{code}")
    public Mono<ResponseEntity<ProductDTO>> getCustomerByCode(@PathVariable String code) {
        LOGGER.info("Searching for product with code {}", code);
        return productService.getProductByEncryptedCode(code)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/code/{customerCode}")
    public Flux<ProductDTO> getProductsByCustomerByCode(@PathVariable ("customerCode") String customerCode) {
        LOGGER.info("Searching for products by customer code with code {}", customerCode);
        return productService.getProductsByCustomerByCode(customerCode);
    }

    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> saveCustomer(@RequestBody @Valid ProductDTO productDTO) {
        LOGGER.info("Saving new customer");
        return productService.save(productDTO)
                .map(savedProduct -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(savedProduct)
                );
    }

    @PutMapping("/{id}")
    public Mono<ProductDTO> updateProduct(@Min(1) @PathVariable UUID id, @RequestBody @Valid ProductDTO productDTO) {
        LOGGER.info("Updating a product with {}", id);
        return productService.update(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@Min(1) @PathVariable UUID id) {
        LOGGER.info("Deleting a product with {}", id);
        return productService.deleteById(id);
    }
}
