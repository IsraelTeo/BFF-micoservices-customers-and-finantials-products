package com.microservice.BFF.controller;

import com.microservice.BFF.connector.payload.CustomerProductsDTO;
import com.microservice.BFF.service.BFFService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/bff")
public class BFFController {

    BFFService bffService;

    @GetMapping("/{customerCode}")
    Mono<CustomerProductsDTO> getCustomerAndProducts(@PathVariable("customerCode") String customerCode){
        return bffService.getCustomerAndProducts(customerCode);
    }
}
