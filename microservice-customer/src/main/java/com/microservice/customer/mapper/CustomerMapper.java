package com.microservice.customer.mapper;

import com.microservice.customer.domain.Customer;
import com.microservice.customer.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends Converter<Customer, CustomerDTO> {

    @Override
    CustomerDTO convert(Customer customer);
}
