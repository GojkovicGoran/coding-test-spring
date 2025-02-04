package com.ggcode.ggshopify.services;

import com.ggcode.ggshopify.client.ShopifyClient;
import com.ggcode.ggshopify.exceptions.ShopifyApiException;
import com.ggcode.ggshopify.models.entity.Product;
import com.ggcode.ggshopify.models.entity.Variant;
import com.ggcode.ggshopify.services.impl.SyncServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;






@ExtendWith(MockitoExtension.class)
class SyncServiceImplTest {

    @Mock
    private ShopifyClient shopifyClient;

    private SyncServiceImpl syncService;

    @BeforeEach
    void setUp() {
        syncService = new SyncServiceImpl(shopifyClient);
    }

    @Test
    void syncProducts_WhenProductExists_ShouldUpdate() throws ShopifyApiException {
        // Arrange
        Variant pimVariant = new Variant();
        pimVariant.setSku("SKU123");
        
        Product pimProduct = new Product();
        pimProduct.setVariants(Arrays.asList(pimVariant));

        Variant receiverVariant = new Variant();
        receiverVariant.setSku("SKU123");
        receiverVariant.setId(1L);
        
        Product receiverProduct = new Product();
        receiverProduct.setId(100L);
        receiverProduct.setVariants(Arrays.asList(receiverVariant));

        when(shopifyClient.getPimProducts()).thenReturn(Arrays.asList(pimProduct));
        when(shopifyClient.getReceiverProducts()).thenReturn(Arrays.asList(receiverProduct));

        // Act
        syncService.syncProducts();

        // Assert
        verify(shopifyClient).updateProduct(any(Product.class), eq("100"));
        verify(shopifyClient, never()).createProduct(any(Product.class));
    }

    @Test
    void syncProducts_WhenProductDoesNotExist_ShouldCreate() throws ShopifyApiException {
        // Arrange
        Variant pimVariant = new Variant();
        pimVariant.setSku("SKU123");
        
        Product pimProduct = new Product();
        pimProduct.setVariants(Arrays.asList(pimVariant));

        when(shopifyClient.getPimProducts()).thenReturn(Arrays.asList(pimProduct));
        when(shopifyClient.getReceiverProducts()).thenReturn(Collections.emptyList());

        // Act
        syncService.syncProducts();

        // Assert
        verify(shopifyClient).createProduct(any(Product.class));
        verify(shopifyClient, never()).updateProduct(any(Product.class), anyString());
    }

    @Test
    void syncProducts_WhenProductHasNoVariants_ShouldSkip() throws ShopifyApiException {
        // Arrange
        Product pimProduct = new Product();
        pimProduct.setId(1L);

        when(shopifyClient.getPimProducts()).thenReturn(Arrays.asList(pimProduct));
        when(shopifyClient.getReceiverProducts()).thenReturn(Collections.emptyList());

        // Act
        syncService.syncProducts();

        // Assert
        verify(shopifyClient, never()).createProduct(any(Product.class));
        verify(shopifyClient, never()).updateProduct(any(Product.class), anyString());
    }

    @Test
    void syncProducts_WhenVariantHasNoSku_ShouldSkip() throws ShopifyApiException {
        // Arrange
        Variant pimVariant = new Variant();
        Product pimProduct = new Product();
        pimProduct.setId(1L);
        pimProduct.setVariants(Arrays.asList(pimVariant));

        when(shopifyClient.getPimProducts()).thenReturn(Arrays.asList(pimProduct));
        when(shopifyClient.getReceiverProducts()).thenReturn(Collections.emptyList());

        // Act
        syncService.syncProducts();

        // Assert
        verify(shopifyClient, never()).createProduct(any(Product.class));
        verify(shopifyClient, never()).updateProduct(any(Product.class), anyString());
    }
}