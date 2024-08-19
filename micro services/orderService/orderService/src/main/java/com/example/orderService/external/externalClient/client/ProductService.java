package com.example.orderService.external.externalClient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE/api/v1/product")
public interface ProductService {

    @PutMapping("/list/reducedQuanitiy/{id}")
    ResponseEntity<String> reducedQuantity(@PathVariable("id") long productId, @RequestParam long quantity);
}
