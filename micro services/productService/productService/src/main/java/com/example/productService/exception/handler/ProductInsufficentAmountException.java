package com.example.productService.exception.handler;

import lombok.Data;

@Data
public class ProductInsufficentAmountException extends RuntimeException  {
    private String errorCode;
    public ProductInsufficentAmountException(String message,String errorCode){
        super(message);
        this.errorCode=errorCode;
    }

}
