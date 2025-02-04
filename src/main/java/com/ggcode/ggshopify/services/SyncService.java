package com.ggcode.ggshopify.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggcode.ggshopify.models.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

public interface SyncService {

    public void syncProducts() throws Exception;
}
