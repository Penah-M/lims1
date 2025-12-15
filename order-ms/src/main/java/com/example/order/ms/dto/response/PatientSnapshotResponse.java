package com.example.order.ms.dto.response;

import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class PatientSnapshotResponse {

      String fullName;
      Gender gender;
      Integer age;
}