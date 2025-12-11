package com.example.patient.service.dao.entity;


import com.example.patient.service.enums.Gender;
import com.example.patient.service.enums.PatientStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
@FieldDefaults(level = PRIVATE)
@Builder(toBuilder = true)
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String firstName;

    String lastName;

    String fatherName;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String phone;
    LocalDate birthday;

    String documentNumber;

    @Enumerated(EnumType.STRING)
    PatientStatus status;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updateAt;

}
