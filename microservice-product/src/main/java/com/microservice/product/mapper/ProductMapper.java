package com.microservice.product.mapper;

import com.microservice.product.domain.Product;
import com.microservice.product.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Converter<Product, ProductDTO> {

    @Override
    ProductDTO convert(Product product);
}
