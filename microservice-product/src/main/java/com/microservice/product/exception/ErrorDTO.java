package com.microservice.product.exception;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorDTO(
        String description,
        List<String> reasons
) {
}
