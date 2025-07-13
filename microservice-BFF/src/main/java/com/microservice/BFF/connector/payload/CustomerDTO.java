package com.microservice.BFF.connector.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerDTO(
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
