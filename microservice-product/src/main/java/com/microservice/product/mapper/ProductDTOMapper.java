package com.microservice.product.mapper;

import com.microservice.product.domain.Product;
import com.microservice.product.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper extends Converter<ProductDTO, Product> {

    @Override
    Product convert(ProductDTO productDTO);
}
