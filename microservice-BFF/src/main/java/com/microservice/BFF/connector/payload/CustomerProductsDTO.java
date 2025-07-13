package com.microservice.BFF.connector.payload;

import lombok.Builder;

import java.util.List;

@Builder
public record CustomerProductsDTO(
        CustomerDTO customerDTO,
        List<ProductDTO> products
) {
}