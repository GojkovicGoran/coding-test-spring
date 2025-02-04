//package com.ggcode.ggshopify.repositories;
//
//import com.ggcode.ggshopify.models.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface ProductRepository extends JpaRepository<Product, Long> {
//    Optional<Product> findBySku(String sku); // Find product by SKU
//    Optional<Product> findByShopifyId(String shopifyId); // Find product by Shopify ID
//}