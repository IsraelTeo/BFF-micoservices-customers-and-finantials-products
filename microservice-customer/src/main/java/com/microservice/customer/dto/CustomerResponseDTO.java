package com.microservice.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CustomerResponseDTO(
        @JsonProperty("encrypted_code")
        String encryptedCode,

        String name,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("document_type")
        String documentType,

        @JsonProperty("document_number")
        String documentNumber
) {
}
