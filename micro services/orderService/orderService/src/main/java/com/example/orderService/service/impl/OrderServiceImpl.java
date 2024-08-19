package com.example.orderService.service.impl;

import com.example.orderService.entity.OrderEntity;
import com.example.orderService.exception.CustomException;
import com.example.orderService.external.externalClient.client.ProductService;
import com.example.orderService.model.OrderRequest;
import com.example.orderService.model.OrderResponse;
import com.example.orderService.repo.OrderRepo;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class OrderServiceImpl implements  OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductService productService;

 //   private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //order entity save dta with status order created
        // logger.info(new ParameterizedMessage("placed a order with id {}",orderRequest.getProductId()));
        //product service from product micro service
        productService.reducedQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Created Order with Status Created ");

        OrderEntity order = OrderEntity.builder()
                .amount(orderRequest.getTotalAmount())
                .orderState("CRATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        orderRepo.save(order);
        //  logger.info(new ParameterizedMessage("placed a order with id {}",orderRequest.getProductId()));
        //    log.info(new ParameterizedMessage("placed a order with id '{}'", orderRequest.getProductId()));

        //product service-block products reduced the quantity

        //payment service-payment succes else cancel
        log.info("Calling payment Service to complete the payment!");


        String orderStatus = null;
        try {
            log.info("Payment done Successfully Changing the order status ");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occured in payment.()Changing order status failed");
            orderStatus = "PAYMENT_FAILED";
        }
        order.setOrderState(orderStatus);
        orderRepo.save(order);
        log.info("end of payment service!");

        return order.getId();
    }


    @Override
        public List<OrderResponse> findAllOrders() {
            List<OrderEntity> products = orderRepo.findAll();
            List<OrderResponse> productDTOs = new ArrayList<>();
            for (OrderEntity product : products) {
                productDTOs.add(mapToDTO(product));
            }
            return productDTOs;
        }
    private OrderResponse mapToDTO(OrderEntity order) {
        return new OrderResponse(
                order.getId(),order.getOrderDate(),order.getOrderState(),order.getAmount()
        );

    }


}