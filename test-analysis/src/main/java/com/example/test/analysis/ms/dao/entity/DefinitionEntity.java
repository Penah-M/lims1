package com.example.test.analysis.ms.dao.entity;


import com.lims.common.enums.SampleType;
import com.lims.common.enums.TestStatus;
import com.lims.common.enums.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = PRIVATE)
@Table(name = "test_definitions")
@Builder(toBuilder = true)
public class DefinitionEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String codeShort;
    String codeLong;
    String name;
    String description;

    @Enumerated(EnumType.STRING)
    Unit unit;

    @Enumerated(EnumType.STRING)
    SampleType sampleType;

    Integer tat;

    BigDecimal price;

    @Enumerated(EnumType.STRING)
    TestStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updateAt;
}
