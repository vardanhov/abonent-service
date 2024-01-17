package com.abonent.controller;


import com.abonent.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api")
public class DetailController {
    private DetailService detailService;

    @Autowired
    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/showAbonents/")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> showAbonents(@RequestParam String cellId) throws ExecutionException, InterruptedException {

        CompletableFuture<Map<String, Object>> abonents = detailService.getAbonentsAndProfilesByCellId(cellId);

        return abonents.thenApply(result -> new ResponseEntity<>(result, HttpStatus.OK));
    }
}
