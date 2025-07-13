package com.microservice.BFF.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BFFException extends RuntimeException {

    HttpStatus status;

    String description;

    List<String> reasons;

    public BFFException(ApiErrorEnum error) {
        this.status = error.getStatus();
        this.description = error.getMessage();
    }
}
