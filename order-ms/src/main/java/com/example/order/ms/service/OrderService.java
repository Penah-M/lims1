package com.example.order.ms.service;

import com.example.order.ms.dto.request.LabResultUpdateRequest;
import com.example.order.ms.dto.request.OrderCreateRequest;
import com.example.order.ms.dto.response.LabOrderResponse;
import com.example.order.ms.dto.response.OrderCreateResponse;
import com.example.order.ms.dto.response.ReceiptResponse;

public interface OrderService {



    OrderCreateResponse createOrder(OrderCreateRequest request);

    ReceiptResponse getReceipt(Long orderId);

    void cancelOrder(Long orderId);

    /* =========================
       LAB
       ========================= */

    LabOrderResponse getLabOrder(Long orderId);

    void updateTestResult(Long orderTestId, LabResultUpdateRequest request);
}
