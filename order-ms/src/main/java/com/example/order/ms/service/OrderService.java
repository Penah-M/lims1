package com.example.order.ms.service;

import com.example.order.ms.dto.request.LabResultUpdateRequest;
import com.example.order.ms.dto.request.OrderAddTestRequest;
import com.example.order.ms.dto.request.OrderCreateRequest;
import com.example.order.ms.dto.request.RemoveOrderTestsRequest;
import com.example.order.ms.dto.response.LabOrderResponse;
import com.example.order.ms.dto.response.OrderCreateResponse;
import com.example.order.ms.dto.response.ReceiptResponse;

public interface OrderService {



    OrderCreateResponse createOrder(OrderCreateRequest request);

    ReceiptResponse getReceipt(Long orderId);

    void cancelOrder(Long orderId);
    ReceiptResponse  addTestsToOrder(
            Long orderId,
            OrderAddTestRequest request
    ) ;

    /* =========================
       LAB
       ========================= */

    LabOrderResponse getLabOrder(Long orderId);

    void updateTestResult(Long orderTestId, LabResultUpdateRequest request);
    void deleteOrder(Long orderId);

    ReceiptResponse removeTestsFromOrder(
            Long orderId,
            RemoveOrderTestsRequest request
    );

}
