package com.example.orderService.controller;

import com.example.orderService.model.OrderRequest;
import com.example.orderService.model.OrderResponse;
import com.example.orderService.service.impl.OrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<List<OrderResponse>> getAllProducts() {
        List<OrderResponse> orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        try{
            long orderId=orderService.placeOrder(orderRequest);
           // log.info("order Id:{}");
            return new ResponseEntity<>(orderId, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }


}