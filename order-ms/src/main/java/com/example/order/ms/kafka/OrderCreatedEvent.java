package com.example.order.ms.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderCreatedEvent {
    private UUID orderId;
    private UUID patientId;
    private UUID doctorId;
    private Instant createdAt;
    private List<OrderItemEvent> items;
}