package com.ggcode.ggshopify.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggcode.ggshopify.models.entity.Product;
import com.ggcode.ggshopify.services.impl.ShopifyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;


class ShopifyServiceImplTest {

    @InjectMocks
    private ShopifyServiceImpl shopifyService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void getProducts_ShouldReturnNull() {
        JsonNode result = shopifyService.getProducts("testStore", "testToken");
        assertNull(result);
    }

    @Test
    void createOrUpdateProduct_ShouldNotThrowException() {
        JsonNode mockProduct = objectMapper.createObjectNode();
        assertDoesNotThrow(() -> 
            shopifyService.createOrUpdateProduct("testStore", "testToken", mockProduct, "123")
        );
    }

    @Test
    void getProductById_ShouldReturnNull() {
        Product result = shopifyService.getProductById(1L);
        assertNull(result);
    }

    @Test
    void deleteProduct_ShouldNotThrowException() {
        assertDoesNotThrow(() -> 
            shopifyService.deleteProduct(1L)
        );
    }
}