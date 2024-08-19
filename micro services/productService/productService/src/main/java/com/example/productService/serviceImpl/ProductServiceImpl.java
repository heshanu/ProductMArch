package com.example.productService.serviceImpl;

import com.example.productService.entity.ProductEntity;
import com.example.productService.exception.ProductServiceCustomException;
import com.example.productService.exception.handler.ProductInsufficentAmountException;
import com.example.productService.model.ProductRequest;
import com.example.productService.model.ProductResponse;
import com.example.productService.repo.ProductRepo;
import com.example.productService.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Long addProduct(ProductRequest request) {
        log.info("inside the add product service impl");
        ProductEntity product=ProductEntity.builder()
                .productName(request.getProductName())
                .price(request.getPrice())
                .quantity(request.getQuantity()).build();


        productRepo.save(product);

        log.info("product created!");
        return product.getProductId();
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductEntity> products = productRepo.findAll();
        List<ProductResponse> productDTOs = new ArrayList<>();
        for (ProductEntity product : products) {
            productDTOs.add(mapToDTO(product));
        }
        return productDTOs;
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("inside the getProductByI",productId);
        ProductEntity product=productRepo.findById(productId).orElseThrow(()->new ProductServiceCustomException("Product wuth given id isnot avaialbe", "Product_Not_Found"));

        ProductResponse productResponse=new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);

        return productResponse;
    }

    @Override
    public void reducedQuantity(long productId, long quantity) {
        log.info("Reduced Quantity{} for Id:{}",quantity,productId);

        ProductEntity product=productRepo
                .findById(productId)
                .orElseThrow(()->
                        new ProductInsufficentAmountException("Product not Found","PRODUCT NOT FOUND"));

        if(product.getQuantity()<quantity){
            throw new ProductInsufficentAmountException("Product doesnot have sufficent Quantity","PRODUCT NOT FOUND");
        }

        product.setQuantity(product.getQuantity()-quantity);
        productRepo.save(product);
        log.info("Product Quantity {} Updated Successfully for productId {}",quantity,product.getProductId());
    }


    private ProductResponse mapToDTO(ProductEntity product) {
        return new ProductResponse(product.getProductName(), product.getProductId(),product.getQuantity(), product.getPrice());
    }

//    @Override
//    public List<ProductResponse> getAllProducts() {
//        log.info("inside the getall products");
//
//        List<ProductResponse> prList=productRepo.findAll();
//
//        return prList;
//        log.info("get all products");
//
//    }


//    @Override
//    public long addProduct(ProductRequest request) {
//        log.info("inside the add product service impl");
//        ProductEntity product=ProductEntity.builder()
//                .productName(request.getName())
//                .price(request.getPrice())
//                .quantity(request.getQuantity()).build();
//        productRepo.save(product);
//    }

}
