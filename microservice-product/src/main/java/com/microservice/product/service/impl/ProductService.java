package com.microservice.product.service.impl;

import com.microservice.product.domain.Product;
import com.microservice.product.dto.ProductDTO;
import com.microservice.product.exception.ApiErrorEnum;
import com.microservice.product.exception.ProductException;
import com.microservice.product.repository.IProductRepository;
import com.microservice.product.repository.ProductMapper;
import com.microservice.product.service.IProductService;
import com.microservice.product.util.code.EncryptedCode;
import com.microservice.product.util.code.Encryptor;
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
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {

    static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    IProductRepository productRepository;

    ConversionService conversionService;

    @Override
    public Mono<ProductDTO> getProductById(UUID id) {
        return productRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(
                                new ProductException(ApiErrorEnum.PRODUCT_NOT_FOUND)
                        )
                )
                .mapNotNull(ProductMapper::toDTO);
    }

    @Override
    public Mono<ProductDTO> getProductByEncryptedCode(String encryptedCodeSent) {
        Mono<Product> productFind = productRepository.findProductByEncryptedCode(encryptedCodeSent);

        return productFind.switchIfEmpty(
                        Mono.error(
                                new ProductException(ApiErrorEnum.PRODUCT_NOT_FOUND)
                        )
                )
                .mapNotNull(ProductMapper::toDTO);
    }

    public Flux<ProductDTO> getProductsByCustomerByCode (String customerCode) {
        return productRepository.findProductsByCustomerCode(customerCode)
                .mapNotNull(ProductMapper::toDTO);
    }

    @Override
    public Mono<ProductDTO> save(ProductDTO productDTO) {
        if (Objects.isNull(productDTO)) {
            LOGGER.debug("Attempted to save a null productDto.");
            throw new ProductException(ApiErrorEnum.PRODUCT_NOT_FOUND);
        }

        Product product = ProductMapper.toEntity(productDTO);

        if (product == null) {
            LOGGER.error("Conversion from productDTO to product entity failed.");
            throw new ProductException(ApiErrorEnum.PRODUCT_NOT_FOUND);
        }

        product.setCreationDate(LocalDate.now());

        String rawCode = EncryptedCode.generateRandomCode(8);
        String encryptedCode = Encryptor.encrypt(rawCode);

        product.setEncryptedCode(encryptedCode);

        Mono<Product> result = productRepository.save(product);

        return result.mapNotNull(ProductMapper::toDTO);
    }

    @Override
    public Mono<ProductDTO> update(UUID id, ProductDTO productDTO) {
        if (!productRepository.existsById(id).block()) {
            LOGGER.debug("Product with ID {} not found. Cannot update.", id);
            throw new ProductException(ApiErrorEnum.PRODUCT_NOT_FOUND);
        }

        Product product = conversionService.convert(productDTO, Product.class);

        Mono<Product> productUpdated = productRepository.save(Objects.requireNonNull(product ));

        return productUpdated.mapNotNull(ProductMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        if (!productRepository.existsById(id).block()) {
            LOGGER.debug("Product with ID {} not found. Cannot delete.", id);
            throw new ProductException(ApiErrorEnum.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(id);
        return Mono.empty();
    }

    @Override
    public Flux<ProductDTO> getProducts() {
        Flux<Product> products = productRepository.findAll();
        return products.mapNotNull(ProductMapper::toDTO);
    }




    /*public Mono<Boolean> validateCode(String encryptedCodeSent, UUID productId) {
        LOGGER.info("Validating code for products with ID: {}", productId);
        return productRepository.findEncryptedCodeById(productId)
                .flatMap(
                        encryptedCodeFromDb -> {
                            try {
                                LOGGER.debug("Decrypting code from database.");
                                String decryptedDbCode = Encryptor.decrypt(encryptedCodeFromDb);

                                LOGGER.debug("Decrypting code from request.");
                                String decryptedSentCode = Encryptor.decrypt(encryptedCodeSent);

                                boolean isValid = decryptedDbCode.equals(decryptedSentCode);
                                LOGGER.info("Code validation result for product  {}: {}", productId, isValid);

                                return Mono.just(isValid);
                            } catch (Exception e) {
                                LOGGER.error("Error during code validation for product {}: {}", productId, e.getMessage(), e);
                                return Mono.just(false);
                            }
                        })
                .switchIfEmpty(
                        Mono.fromCallable(() -> {
                            LOGGER.warn("No encrypted code found for product with ID: {}", productId);
                            return false;
                        })
                );
    }*/
}
