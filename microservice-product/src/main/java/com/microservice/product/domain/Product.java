package com.microservice.product.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    UUID id;

    @Column(value = "encrypted_code")
    String encryptedCode;

    String name;

    BigDecimal balance;

    @Column(value = "product_type")
    String productType;

    @Column(value = "customer_code")
    String customerCode;

    @Column(value = "creation_date")
    LocalDate creationDate;
}
