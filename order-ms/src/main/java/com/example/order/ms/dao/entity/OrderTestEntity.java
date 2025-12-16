package com.example.order.ms.dao.entity;

import com.example.order.ms.enums.OrderTestStatus;
import com.lims.common.enums.Unit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "order_tests")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderTestEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
     OrderEntity order;

    @Column(name = "test_definition_id", nullable = false)
     Long testDefinitionId;

    // Snapshot
    @Column(name = "test_name", nullable = false, length = 200)
     String testName;

    @Column(name = "test_code", length = 50)
     String testCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", length = 50)
    Unit unit;

    @Column(nullable = false, precision = 12, scale = 2)
     BigDecimal price;

    @Column(name = "min_value", precision = 12, scale = 3)
     BigDecimal minValue;

    @Column(name = "max_value", precision = 12, scale = 3)
     BigDecimal maxValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
     OrderTestStatus status;

    // TODO [RESULT-MS]: Temporary. Result MS gelende bu field istifadeden cixarilacaq.
    @Column(columnDefinition = "TEXT")
     String result;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
     LocalDateTime createdAt;
}
