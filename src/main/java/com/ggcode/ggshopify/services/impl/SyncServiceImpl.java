package com.ggcode.ggshopify.services.impl;

import com.ggcode.ggshopify.client.ShopifyClient;
import com.ggcode.ggshopify.controllers.SyncController;
import com.ggcode.ggshopify.exceptions.ShopifyApiException;
import com.ggcode.ggshopify.models.entity.Product;
import com.ggcode.ggshopify.services.SyncService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SyncServiceImpl implements SyncService {

    private static final Logger logger = LoggerFactory.getLogger(SyncServiceImpl.class);

    private final ShopifyClient shopifyClient;

    public SyncServiceImpl(ShopifyClient shopifyClient) {
        this.shopifyClient = shopifyClient;
    }


    public void syncProducts() {
        List<Product> pimProducts = shopifyClient.getPimProducts();
        List<Product> receiverProducts = shopifyClient.getReceiverProducts();

        Map<String, Product> receiverProductMap = receiverProducts.stream()
                .collect(Collectors.toMap(
                        p -> p.getVariants().get(0).getSku(),
                        Function.identity(),
                        (existingProduct, newProduct) -> existingProduct
                ));

        for (Product pimProduct : pimProducts) {
            if (pimProduct.getVariants() == null || pimProduct.getVariants().isEmpty()) {
                log.error("Product has no variants: {}", pimProduct.getId());
                continue;
            }

            String sku = pimProduct.getVariants().get(0).getSku();
            if (sku == null) {
                log.error("Product variant has no SKU: {}", pimProduct.getId());
                continue;
            }

            Product receiverProduct = receiverProductMap.get(sku);

            try {
                if (receiverProduct != null) {
                    log.info("Updating product with SKU: {}", sku);
                    // Copy variant IDs from receiver product to PIM product
                    for (int i = 0; i < Math.min(pimProduct.getVariants().size(), receiverProduct.getVariants().size()); i++) {
                        pimProduct.getVariants().get(i).setId(receiverProduct.getVariants().get(i).getId());
                    }
                    // Set the product ID from receiver
                    pimProduct.setId(receiverProduct.getId());
                    shopifyClient.updateProduct(pimProduct, receiverProduct.getId().toString());
                } else {
                    log.info("Creating new product with SKU: {}", sku);
                    // Clear any existing IDs before creation
                    pimProduct.setId(null);
                    pimProduct.getVariants().forEach(variant -> variant.setId(null));
                    shopifyClient.createProduct(pimProduct);
                }
            } catch (ShopifyApiException e) {
                log.error("Failed to sync product with SKU: {}. Error: {}", sku, e.getMessage());
                throw new RuntimeException("Failed to sync product", e);
            }
        }
    }
}
