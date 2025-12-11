package com.example.order.ms.service;

import com.example.order.ms.client.PatientClient;
import com.example.order.ms.client.RangeClient;
import com.example.order.ms.dao.entity.OrderEntity;
import com.example.order.ms.dao.repository.OrderRepository;
import com.example.order.ms.dto.request.OrderCreateRequest;
import com.example.order.ms.dto.request.OrderTestRequest;
import com.example.order.ms.dto.response.OrderCreateResponse;
import com.example.order.ms.dto.response.OrderItemResponse;
import com.example.order.ms.dto.response.OrderResponse;
import com.example.order.ms.enums.OrderStatus;
import com.lims.common.dto.response.patient.PatientResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE,makeFinal = true)
public class OrderImpl {

    OrderRepository orderRepository;
    PatientClient patientClient;
    RangeClient rangeClient;


}
