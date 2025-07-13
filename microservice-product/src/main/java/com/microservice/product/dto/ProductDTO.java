package com.microservice.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductDTO(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 80, message = "Name must not exceed 80 characters")
        String name,

        @NotNull(message = "Balance must not be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be a positive number")
        BigDecimal balance,

        @NotBlank(message = "Product type must not be blank")
        @Size(max = 80, message = "Product type must not exceed 80 characters")
        @JsonProperty("product_type")
        String productType,

        @NotNull(message = "Customer code must not be null")
        @JsonProperty("customer_code")
        String customerCode
) {
}
