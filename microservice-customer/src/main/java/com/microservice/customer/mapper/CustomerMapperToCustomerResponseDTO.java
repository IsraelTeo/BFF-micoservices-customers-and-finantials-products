package com.microservice.customer.mapper;

import com.microservice.customer.domain.Customer;
import com.microservice.customer.dto.CustomerResponseDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerMapperToCustomerResponseDTO extends Converter<Customer, CustomerResponseDTO> {

    @Override
    CustomerResponseDTO convert(Customer customer);
}
