package com.example.patient.service.dto.response;

import com.example.patient.service.enums.Gender;
import com.example.patient.service.enums.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PatientResponse {
    Long id;

    String firstName;

    String lastName;

    String fatherName;

    Gender gender;

    String phone;

    LocalDate birthday;

    String documentNumber;

    PatientStatus status;

    LocalDateTime createdAt;

    LocalDateTime updateAt;
}
