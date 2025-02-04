package com.ggcode.ggshopify.models.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Product {

    private Long id;

    private String sku;

    private String title;

    private String description;

    private BigDecimal price;

    private String shopifyId;

    public List<Variant> variants;
}