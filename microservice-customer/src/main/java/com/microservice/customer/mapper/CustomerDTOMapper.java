package com.microservice.customer.mapper;

import com.microservice.customer.domain.Customer;
import com.microservice.customer.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerDTOMapper extends Converter<CustomerDTO, Customer> {

    @Override
    Customer convert(CustomerDTO customerDTO);
}
