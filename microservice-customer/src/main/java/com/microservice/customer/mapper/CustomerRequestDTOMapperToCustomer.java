package com.microservice.customer.mapper;

import com.microservice.customer.domain.Customer;
import com.microservice.customer.dto.CustomerRequestDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerRequestDTOMapperToCustomer extends Converter<CustomerRequestDTO, Customer> {

    @Override
    Customer convert(CustomerRequestDTO customer);
}
