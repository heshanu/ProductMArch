package com.example.productService.exception.handler;

import com.example.productService.exception.ProductItemDuplicationError;
import com.example.productService.exception.ProductServiceCustomException;
import com.example.productService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseNotSufficentAmountExceptionHandler {

        @ExceptionHandler(ProductItemDuplicationError.class)
        public ResponseEntity<ErrorResponse> handleProductServiceException(ProductInsufficentAmountException exception){
            return new ResponseEntity<>(new ErrorResponse().builder()
                    .errorMessage(exception.getMessage())
                    .errorCode(exception.getErrorCode())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

