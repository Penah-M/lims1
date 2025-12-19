package com.lims.common.dto.response.patient;

import com.lims.common.enums.Gender;
import com.lims.common.enums.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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

    String fin;

    PatientStatus status;

    LocalDateTime createdAt;

    LocalDateTime updateAt;
}
