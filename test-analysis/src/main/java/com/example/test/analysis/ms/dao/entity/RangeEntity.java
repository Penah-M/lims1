package com.example.test.analysis.ms.dao.entity;

import com.example.test.analysis.ms.enums.Gender;
import com.example.test.analysis.ms.enums.PregnancyStatus;
import com.example.test.analysis.ms.enums.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = PRIVATE)
@Table(name = "reference_ranges")
@Builder(toBuilder = true)
public class RangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "test_definition_id")
    DefinitionEntity testDefinition;

    Double minValue;
    Double maxValue;

    @Enumerated(EnumType.STRING)
    Gender gender;

    Integer ageMin;

    Integer ageMax;

    @Enumerated(EnumType.STRING)
    PregnancyStatus pregnancyStatus;

    @Enumerated(EnumType.STRING)
    Unit unit;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updateAt;
}
