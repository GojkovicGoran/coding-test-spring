package com.ggcode.ggshopify.client;

import com.ggcode.ggshopify.config.ShopifyConfig;
import com.ggcode.ggshopify.models.entity.Product;
import com.ggcode.ggshopify.services.impl.SyncServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShopifyClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SyncServiceImpl syncService;

    @InjectMocks
    private ShopifyClient shopifyClient;

    private ShopifyConfig shopifyConfig;

    private final String storeName = "backend-test-pim";
    private final String accessToken = "<ACCESS-TOKEN>";
    private final String baseUrl = "https://backend-test-pim.myshopify.com/admin/api/2023-01/products.json";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shopifyConfig = new ShopifyConfig();
        ShopifyConfig.PimConfig pimConfig = new ShopifyConfig.PimConfig();
        pimConfig.setStoreName(storeName);
        pimConfig.setAccessToken(accessToken);
        shopifyConfig.setPim(pimConfig);
        
        shopifyClient = new ShopifyClient(restTemplate, shopifyConfig);
    }

    @Test
    void testGetPimProducts() {
        // Setup test data
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");

        String expectedUrl = String.format("https://%s.myshopify.com/admin/api/2023-10/products.json", storeName);

        // Mock response
        ShopifyClient.ProductListResponse mockResponse = new ShopifyClient.ProductListResponse();
        mockResponse.setProducts(List.of(product));

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ShopifyClient.ProductListResponse.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // Execute method under test
        List<Product> result = shopifyClient.getPimProducts();

        // Verify results
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getTitle());
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ShopifyClient.ProductListResponse.class)
        );
    }

    @Test
    void testGetProducts() {
        // Setup test data
        String testStoreName = "test-store";

        String expectedUrl = "https://test-store.myshopify.com/admin/api/2023-10/products.json";
        
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");

        // Mock response
        ShopifyClient.ProductListResponse mockResponse = new ShopifyClient.ProductListResponse();
        mockResponse.setProducts(List.of(product));

        // Stub REST template call
        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                argThat(request -> 
                    request.getHeaders().getFirst("X-Shopify-Access-Token").equals(accessToken)
                ),
                eq(ShopifyClient.ProductListResponse.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // Execute method under test
        List<Product> result = shopifyClient.getProducts(testStoreName, accessToken);

        // Verify results
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getTitle());
        verify(restTemplate).exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET), 
                argThat(request ->
                    accessToken.equals(request.getHeaders().getFirst("X-Shopify-Access-Token"))
                ),
                eq(ShopifyClient.ProductListResponse.class)
        );
    }

    @Test
    void shouldUseCorrectAccessToken() {
        // Execute
        syncService.syncProducts();

        // Verify
        verify(restTemplate).exchange(
                anyString(),
                eq(HttpMethod.POST),
                argThat(request -> 
                    accessToken.equals(request.getHeaders().getFirst("X-Shopify-Access-Token"))
                ),
                eq(Void.class)
        );
    }
}