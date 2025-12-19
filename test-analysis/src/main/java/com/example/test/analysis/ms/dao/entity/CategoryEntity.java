package com.example.test.analysis.ms.dao.entity;

import com.lims.common.enums.TestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = PRIVATE)
@Table(name = "test_categories")
@Builder(toBuilder = true)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;

    String code;

    @Enumerated(EnumType.STRING)
    TestStatus status;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updateAt;


}
