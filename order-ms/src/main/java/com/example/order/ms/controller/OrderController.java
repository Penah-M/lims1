package com.example.order.ms.controller;

import com.example.order.ms.dto.request.OrderCreateRequest;
import com.example.order.ms.dto.response.OrderCreateResponse;
import com.example.order.ms.dto.response.OrderCreateResponse1;
import com.example.order.ms.service.OrderImpl;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE,makeFinal = true)
public class OrderController {

    OrderImpl order;
    @PostMapping("/create")
    public OrderCreateResponse createResponse(@RequestBody OrderCreateRequest request){
        return order.createResponse(request);
    }


    @PostMapping("/newCreate")
    public OrderCreateResponse1 createOrder(@RequestBody  OrderCreateRequest request){
        return order.createOrder(request);
    }
}
