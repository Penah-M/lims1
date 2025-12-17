package com.example.order.ms.mapper;

import com.example.order.ms.dao.entity.OrderEntity;
import com.example.order.ms.dto.response.PatientSnapshotResponse;
import com.lims.common.dto.response.patient.PatientResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PatientSnapshotMapper {

    public void mapPatientSnapshot(OrderEntity order, PatientResponse patient) {

        if (order == null || patient == null) {
            throw new IllegalArgumentException("Order ve Patient null ola bilmez");
        }

        order.setPatientId(patient.getId());
        order.setPatientFullName(
                patient.getFirstName() + " " + patient.getLastName()
        );
        order.setPatientGender(patient.getGender());
        order.setPatientBirthDate(patient.getBirthday());
    }
    public PatientSnapshotResponse fromOrder(OrderEntity order) {

        Integer age = null;
        if (order.getPatientBirthDate() != null) {
            age = Period.between(
                    order.getPatientBirthDate(),
                    LocalDate.now()
            ).getYears();
        }

        return PatientSnapshotResponse.builder()
                .fullName(order.getPatientFullName())
                .gender(order.getPatientGender())
                .age(age)
                .build();
    }
}