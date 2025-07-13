package com.microservice.product.repository;

import com.microservice.product.domain.Product;
import com.microservice.product.dto.ProductDTO;

public class ProductMapper {

    public static Product toEntity(ProductDTO dto) {
        return Product.builder()
                .name(dto.name())
                .balance(dto.balance())
                .productType(dto.productType())
                .customerCode(dto.customerCode())
                .build();
    }

    public static ProductDTO toDTO(Product entity) {
        return ProductDTO.builder()
                .name(entity.getName())
                .balance(entity.getBalance())
                .productType(entity.getProductType())
                .customerCode(entity.getCustomerCode())
                .build();
    }
}
