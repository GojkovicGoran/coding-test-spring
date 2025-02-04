package com.ggcode.ggshopify.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.ggcode.ggshopify.models.entity.Product;
import com.ggcode.ggshopify.services.ShopifyService;
import org.springframework.stereotype.Service;


@Service
public class ShopifyServiceImpl implements ShopifyService {


    @Override
    public JsonNode getProducts(String storeName, String accessToken) {
        return null;
    }

    @Override
    public void createOrUpdateProduct(String storeName, String accessToken, JsonNode product, String productId) {

    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}