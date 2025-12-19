package com.example.test.analysis.ms.mapper;

import com.example.test.analysis.ms.dao.entity.DefinitionEntity;
import com.example.test.analysis.ms.dto.request.DefinitionRequest;
import com.example.test.analysis.ms.dto.request.UpdateDefinitionRequest;
import com.lims.common.enums.TestStatus;
import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefinitionMapper {
    public DefinitionEntity entity(DefinitionRequest request) {
        return DefinitionEntity.builder()
                .codeShort(request.getCodeShort())
                .codeLong(request.getCodeLong())
                .name(request.getName())
                .description(request.getDescription())
                .unit(request.getUnit())
                .sampleType(request.getSampleType())
                .tat(request.getTurnaroundTime())
                .price(request.getPrice())
                .status(TestStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public DefinitionResponse response(DefinitionEntity entity){
        return DefinitionResponse.builder()
                .id(entity.getId())
                .codeShort(entity.getCodeShort())
                .codeLong(entity.getCodeLong())
                .name(entity.getName())
                .description(entity.getDescription())
                .unit(entity.getUnit())
                .sampleType(entity.getSampleType())
                .turnaroundTime(entity.getTat())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updateAt(entity.getUpdateAt())
                .categoryId(entity.getCategory().getId())
                .categoryName(entity.getCategory().getName())
                .build();
    }

    public DefinitionEntity updateEntity(DefinitionEntity entity, UpdateDefinitionRequest request) {
        if (entity == null || request == null) {
            throw new IllegalArgumentException("Entity və Request null ola bilməz");
        }
        return entity.toBuilder()
                .codeShort(request.getCodeShort() != null && !request.getCodeShort().isEmpty()
                        ? request.getCodeShort()
                        : entity.getCodeShort())
                .codeLong(request.getCodeLong() != null && !request.getCodeLong().isEmpty()
                        ? request.getCodeLong()
                        : entity.getCodeLong())
                .name(request.getName() != null && !request.getName().isEmpty()
                        ? request.getName()
                        : entity.getName())
                .description(request.getDescription() != null && !request.getDescription().isEmpty()
                        ? request.getDescription()
                        : entity.getDescription())

                .unit(request.getUnit() != null ? request.getUnit()
                        : entity.getUnit())
                .sampleType(request.getSampleType() != null ? request.getSampleType()
                        : entity.getSampleType())
                .tat(request.getTurnaroundTime() != null
                        ? request.getTurnaroundTime()
                        : entity.getTat())
                .price(request.getPrice() != null
                        ? request.getPrice()
                        : entity.getPrice())
                .status(request.getStatus() != null? request.getStatus()
                        : entity.getStatus())
                .build();

    }



}
