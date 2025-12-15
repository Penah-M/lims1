package com.example.order.ms.controller;

import com.example.order.ms.dto.request.LabResultUpdateRequest;
import com.example.order.ms.dto.response.LabOrderResponse;
import com.example.order.ms.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lab/orders")
public class OrderLabController {

    private final OrderService orderService;

    /* =========================
       GET LAB ORDER
       ========================= */
    @GetMapping("/{orderId}")
    public ResponseEntity<LabOrderResponse> getLabOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.getLabOrder(orderId));
    }

    /* =========================
       UPDATE TEST RESULT
       ========================= */
    @PutMapping("/tests/{orderTestId}/result")
    public ResponseEntity<Void> updateTestResult(
            @PathVariable Long orderTestId,
            @Valid @RequestBody LabResultUpdateRequest request) {

        orderService.updateTestResult(orderTestId, request);
        return ResponseEntity.noContent().build();
    }
}
