package com.ggcode.ggshopify.controllers;

import com.ggcode.ggshopify.services.SyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }


    @PostMapping("/products")
    public ResponseEntity<String> syncProducts() throws Exception {
        try {
            syncService.syncProducts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.debug("In controller syncProducts: ");

        return new ResponseEntity<String>(HttpStatus.OK);
    }

}