package com.example.productService.service;



import com.example.productService.model.ProductRequest;
import com.example.productService.model.ProductResponse;

import java.util.List;

public interface ProductService {
    Long addProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(long productId);

    void reducedQuantity(long productId, long quantity);
}
