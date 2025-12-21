package com.example.patient.service.dto.request;

import com.lims.common.enums.Gender;
import com.lims.common.enums.PatientStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientFilterRequest {

    String firstName;
    String lastName;
    String fin;
    Gender gender;
    PatientStatus status;

    LocalDate birthdayFrom;
    LocalDate birthdayTo;

    LocalDate createdFrom;
    LocalDate createdTo;
}
