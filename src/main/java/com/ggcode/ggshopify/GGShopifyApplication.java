package com.ggcode.ggshopify;

import com.ggcode.ggshopify.config.ShopifyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(ShopifyConfig.class)
@ComponentScan(basePackages = {"com.ggcode.ggshopify"})
public class GGShopifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GGShopifyApplication.class, args);
	}

}
