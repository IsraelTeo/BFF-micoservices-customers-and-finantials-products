package com.microservice.product.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.List;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductException extends RuntimeException {

    HttpStatus status;

    String description;

    List<String> reasons;

    public ProductException(ApiErrorEnum error) {
        this.status = error.getStatus();
        this.description = error.getMessage();
    }
}
