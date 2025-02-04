package com.ggcode.ggshopify.mappers;

import com.ggcode.ggshopify.models.dto.ProductDto;
import com.ggcode.ggshopify.models.entity.Product;

public interface ProductMapper {

    ProductDto productEntityToDto(Product product);

    Product productDtoToEntity(ProductDto productDto);

}
