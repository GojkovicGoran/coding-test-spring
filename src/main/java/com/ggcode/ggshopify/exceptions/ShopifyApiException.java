package com.ggcode.ggshopify.exceptions;


public class ShopifyApiException extends RuntimeException {
    public ShopifyApiException() {
    }

    public ShopifyApiException(String message) {
        super(message);
    }

    public ShopifyApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
