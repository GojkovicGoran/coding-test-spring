package com.ggcode.ggshopify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ggcode.ggshopify"})
public class GGShopifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GGShopifyApplication.class, args);
    }

}
