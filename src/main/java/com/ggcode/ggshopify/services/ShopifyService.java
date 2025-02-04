package com.ggcode.ggshopify.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.ggcode.ggshopify.models.entity.Product;

public interface ShopifyService {

    JsonNode getProducts(String storeName, String accessToken);

    void createOrUpdateProduct(String storeName, String accessToken, JsonNode product, String productId);

    Product getProductById(Long id);

    void deleteProduct(Long id);
}
