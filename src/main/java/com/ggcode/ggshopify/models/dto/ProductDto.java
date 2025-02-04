package com.ggcode.ggshopify.models.dto;


import com.ggcode.ggshopify.models.entity.Variant;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private Long id;

    private String sku;

    private String title;

    private String description;

    private BigDecimal price;

    private String shopifyId;

    private List<Variant> variants;
}
