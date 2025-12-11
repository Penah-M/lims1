package com.example.order.ms.kafka;

import com.example.order.ms.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderStatusChangedEvent {
     UUID orderId;
     OrderStatus previousStatus;
     OrderStatus newStatus;
     UUID changedBy;
     Instant changedAt;
     String reason;
}
