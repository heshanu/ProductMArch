package com.example.orderService.external.externalClient.decoder;

import com.example.orderService.exception.CustomException;
import com.example.orderService.external.externalClient.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;


public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();

          // log.info(new ParameterizedMessage("::{}",response.request().url()));
         // log.info(new ParameterizedMessage("::{}",response.request().headers()));

        try {
            ErrorResponse errorResponse=objectMapper.readValue(response.body().asInputStream(),
                    ErrorResponse.class);
            return new CustomException(
                    errorResponse.getErrorMessage(),
                    errorResponse.getErrorCode(),
                    response.status());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error","INTERNAL_SERVER_ERROR",500);
        }
    }

}