package com.microservice.customer.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    UUID customerId;

    @Column(value = "encrypted_code")
    String encryptedCode;

    @Column
    String name;

    @Column(value = "last_name")
    String lastName;

    @Column(value = "document_type")
    String documentType;

    @Column(value = "encrypted_number")
    String documentNumber;

    @Column(value = "encrypted_code")
    LocalDate creationDate;
}
