package com.microservice.product.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ApiErrorEnum {

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "There are attributes with incorrect values."),

    BAD_FORMAT(HttpStatus.BAD_REQUEST, "The message has an incorrect format."),

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "The product  was not found."),

    PRODUCTS_NOT_FOUND(HttpStatus.NOT_FOUND, "No products were found."),

    EXCEED_NUMBER_REQUEST(HttpStatus.CONFLICT, "The number of requests exceeds the limit."),

    PRODUCT_WITH_SAME_ID(HttpStatus.CONFLICT, "The product  with the same id already exists."),

    PRODUCT_CONVERSION_FAILED(HttpStatus.CONFLICT, "The product conversion failed."),
    ENCRYPTING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "There was an error while encrypting code from product."),
    ;

    HttpStatus status;

    String message;
}
