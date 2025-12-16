package com.example.order.ms.dao.entity;

import com.example.order.ms.enums.OrderStatus;
import com.lims.common.enums.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)


public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @Column(name = "order_number", nullable = false, unique = true, length = 40)
     String orderNumber;

    @Column(name = "patient_id", nullable = false)
     Long patientId;

    // Patient snapshot
    @Column(name = "patient_full_name", nullable = false, length = 200)
     String patientFullName;

    @Column(name = "patient_gender", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    Gender patientGender;

    @Column(name = "patient_birth_date", nullable = false)
     LocalDate patientBirthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
     OrderStatus status;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    BigDecimal totalPrice;


    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;

    @Column(name = "discount_reason")
    private String discountReason;

    @Column(name = "final_price", nullable = false)
    private BigDecimal finalPrice;

    @Column(name = "created_by", nullable = false)
     Long createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
     LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
     String notes;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
     List<OrderTestEntity> tests =new ArrayList<>();
    public void addTest(OrderTestEntity test) {
        tests.add(test);
        test.setOrder(this);
    }
}