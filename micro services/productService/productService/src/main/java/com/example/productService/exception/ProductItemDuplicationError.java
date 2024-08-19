package com.example.productService.exception;

public class ProductItemDuplicationError extends RuntimeException{
    private String errorCode;
    public  ProductItemDuplicationError(String message,String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
