package com.ggcode.ggshopify.models.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Variant {

    private Long id;
    private Long productId;
    private String title;
    private String sku;
    private BigDecimal price;

}
