package com.ggcode.ggshopify.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Data
@Configuration
@ConfigurationProperties(prefix = "shopify")
public class ShopifyConfig {

    private PimConfig pim;
    private ReceiverConfig receiver;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Data
    public static class PimConfig {
        private String storeName;
        private String accessToken;
    }

    @Data
    public static class ReceiverConfig {
        private String storeName;
        private String accessToken;
    }
}

