package com.example.order.ms.dao.entity;

import com.example.order.ms.enums.Unit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "order_tests")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

     Long testId; // Test MS-dən gələn ID

     Unit unit;

     String referenceRange;

     BigDecimal price = BigDecimal.ZERO;

     String status = "PENDING";

     String result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
     OrderEntity order;

    @OneToMany(mappedBy = "orderTest", cascade = CascadeType.ALL, orphanRemoval = true)
     List<OrderTestDetailEntity> orderTestDetails;

}
