package com.example.patient.service.mapper;

import com.example.patient.service.dao.entity.PatientEntity;
import com.example.patient.service.dto.request.PatientRequest;
import com.example.patient.service.dto.response.PatientResponse;
import com.example.patient.service.enums.PatientStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PatientMapper {
    public PatientEntity entity(PatientRequest request) {

        return PatientEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fatherName(request.getFatherName())
                .gender(request.getGender())
                .phone(request.getPhone())
                .birthday(request.getBirthday())
                .documentNumber(request.getDocumentNumber())
                .status(PatientStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public PatientResponse response(PatientEntity entity) {
        return PatientResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .fatherName(entity.getFatherName())
                .gender(entity.getGender())
                .phone(entity.getPhone())
                .birthday(entity.getBirthday())
                .documentNumber(entity.getDocumentNumber())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updateAt(entity.getUpdateAt())
                .build();
    }

    public PatientEntity updateEntity(PatientEntity existingEntity, PatientRequest request) {
        if (existingEntity == null || request == null) {
            throw new IllegalArgumentException("Entity və Request null ola bilməz");
        }

        return existingEntity
                .toBuilder()
                .firstName(request.getFirstName() != null && !request.getFirstName().isEmpty() ? request.getFirstName()
                        : existingEntity.getFirstName())
                .lastName(request.getLastName() != null && !request.getLastName().isEmpty() ? request.getLastName()
                        : existingEntity.getLastName())
                .fatherName(request.getFatherName() != null && !request.getFatherName().isEmpty() ?
                        request.getFatherName() : existingEntity.getFatherName())
                .gender(request.getGender() != null ? request.getGender() : existingEntity.getGender())
                .phone(request.getPhone() != null && !request.getPhone().isEmpty() ? request.getPhone() :
                        existingEntity.getPhone())
                .birthday(request.getBirthday() != null ? request.getBirthday() : existingEntity.getBirthday())
//                .documentNumber(request.getDocumentNumber() != null ? request.getDocumentNumber()
//                :existingEntity.getDocumentNumber())
                .updateAt(LocalDateTime.now())
                .build();
    }
}
