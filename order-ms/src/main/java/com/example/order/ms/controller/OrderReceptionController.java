package com.example.order.ms.controller;

import com.example.order.ms.dto.request.OrderCreateRequest;
import com.example.order.ms.dto.response.OrderCreateResponse;
import com.example.order.ms.dto.response.ReceiptResponse;
import com.example.order.ms.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderReceptionController {

    private final OrderService orderService;

    /* =========================
       CREATE ORDER
       ========================= */
    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(
            @Valid @RequestBody OrderCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    /* =========================
       GET RECEIPT
       ========================= */
    @GetMapping("/{orderId}/receipt")
    public ResponseEntity<ReceiptResponse> getReceipt(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.getReceipt(orderId));
    }

    /* =========================
       CANCEL ORDER
       ========================= */
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId) {

        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
    }
}
