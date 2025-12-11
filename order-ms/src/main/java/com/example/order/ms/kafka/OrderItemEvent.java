package com.example.order.ms.kafka;

import com.example.order.ms.enums.SampleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderItemEvent {
     UUID itemId;
     UUID testId;
     SampleType sampleType;
}