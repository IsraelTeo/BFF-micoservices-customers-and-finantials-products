package com.microservice.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CustomerDTO(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 80, message = "Last name must not exceed 80 characters")
        String name,

        @NotBlank(message = "Name must not be blank")
        @Size(max = 80, message = "Last name must not exceed 80 characters")
        @JsonProperty("last_name")
        String lastName,

        @NotBlank(message = "Document type must not be blank")
        @Size(max = 20, message = "Document type must not exceed 20 characters")
        @JsonProperty("document_type")
        String documentType,

        @NotBlank(message = "Document number must not be blank")
        @Size(max = 12, message = "Document number must not exceed 12 characters")
        @JsonProperty("document_number")
        String documentNumber
) {
}
