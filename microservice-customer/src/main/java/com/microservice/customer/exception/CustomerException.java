package com.microservice.customer.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerException extends RuntimeException{

    HttpStatus status;

    String description;

    List<String> reasons;

    public CustomerException(ApiErrorEnum error) {
        this.status = error.getStatus();
        this.description = error.getMessage();
    }
}
