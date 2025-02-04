package com.ggcode.ggshopify.client;

import com.ggcode.ggshopify.config.ShopifyConfig;
import com.ggcode.ggshopify.models.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShopifyClient {

    private final RestTemplate restTemplate;
    private final ShopifyConfig shopifyConfig;

    Logger logger = Logger.getLogger(ShopifyClient.class.getName());

    public List<Product> getPimProducts() {
        return getProducts(shopifyConfig.getPim().getStoreName(), shopifyConfig.getPim().getAccessToken());
    }

    public List<Product> getReceiverProducts() {
        return getProducts(shopifyConfig.getReceiver().getStoreName(), shopifyConfig.getReceiver().getAccessToken());
    }

    public List<Product> getProducts(String storeName, String accessToken) {
        if (storeName == null || accessToken == null) {
            throw new IllegalArgumentException("Store name and access token cannot be null");
        }

        String url = String.format("https://%s.myshopify.com/admin/api/2023-10/products.json", storeName);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", accessToken);

        ResponseEntity<ProductListResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), ProductListResponse.class);

        log.debug("In ShopifyClient getProducts: {}", response.getBody());

        return Objects.requireNonNull(response.getBody()).getProducts();
    }

    public void createProduct(Product product) {
        modifyProduct(shopifyConfig.getReceiver().getStoreName(), shopifyConfig.getReceiver().getAccessToken(), product, null);
    }

    public void updateProduct(Product product, String productId) {
        modifyProduct(shopifyConfig.getReceiver().getStoreName(), shopifyConfig.getReceiver().getAccessToken(), product, productId);
    }

    private void modifyProduct(String storeName, String accessToken, Product product, String productId) {
        String url = String.format("https://%s.myshopify.com/admin/api/2023-10/products/%s.json",
                storeName, productId != null ? productId : "");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ProductWrapper wrapper = new ProductWrapper(product);
        HttpMethod method = accessToken != null ? HttpMethod.PUT : HttpMethod.POST;

        log.debug("In ShopifyClient modifyProduct: {}", wrapper);
        log.debug("In ShopifyClient method: {}", method);

        restTemplate.exchange(url, method, new HttpEntity<>(wrapper, headers), Void.class);
    }

    private String extractNextPage(HttpHeaders headers) {
        String linkHeader = headers.getFirst("Link");
        if (linkHeader != null) {
            // Extract next page URL from Link header
            // Example: <https://store.myshopify.com/admin/api/2023-10/products.json?page_info=abc123>; rel="next"
            Pattern pattern = Pattern.compile("<([^>]+)>; rel=\"next\"");
            Matcher matcher = pattern.matcher(linkHeader);
            if (matcher.find()) {
                logger.info("Next page URL: " + matcher.group(1));
                return matcher.group(1).split("page_info=")[1];
            }
        }
        return null;
    }

    // DTO classes for JSON serialization
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductListResponse {
        private List<Product> products;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductWrapper {
        private Product product;

        ProductWrapper(Product product) {
            this.product = product;
        }
    }
}
