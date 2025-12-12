package com.example.order.ms.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "order_test_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderTestDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

     String parameterName;

     String parameterValue;

     String referenceRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_test_id")
    OrderTestEntity orderTestEntity;

}
