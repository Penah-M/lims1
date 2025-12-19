package com.example.test.analysis.ms.mapper;

import com.example.test.analysis.ms.dao.entity.DefinitionEntity;
import com.example.test.analysis.ms.dao.entity.RangeEntity;
import com.example.test.analysis.ms.dto.request.RangeRequest;

import com.example.test.analysis.ms.dto.request.UpdateRangeRequest;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import org.springframework.stereotype.Component;



@Component
public class RangeMapper {
    public RangeEntity entity(RangeRequest request, DefinitionEntity definitionentity) {
        return RangeEntity.builder()
                .ageMax(request.getAgeMax())
                .ageMin(request.getAgeMin())
                .gender(request.getGender())
                .pregnancyStatus(request.getPregnancyStatus())
                .testDefinition(definitionentity)
                .minValue(request.getMinValue())
                .maxValue(request.getMaxValue())
                .unit(request.getUnit())
                .build();
    }

    public RangeResponse response(RangeEntity entity) {
        return RangeResponse.builder()
                .id(entity.getId())
                .ageMax(entity.getAgeMax())
                .ageMin(entity.getAgeMin())
                .gender(entity.getGender())
                .pregnancyStatus(entity.getPregnancyStatus())
                .testDefinitionId(entity.getTestDefinition().getId())
                .minValue(entity.getMinValue())
                .maxValue(entity.getMaxValue())
                .status(entity.getTestDefinition().getStatus())
                .unit(entity.getUnit())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdateAt())
                .build();
    }

    public RangeEntity updateEntity(RangeEntity entity, UpdateRangeRequest request) {
        if (entity == null || request == null) {
            throw new IllegalArgumentException("Entity və Request null ola bilməz");
        }

        return entity.toBuilder()
                .minValue(request.getMinValue() != null && request.getMinValue() !=0
                        ? request.getMinValue() : entity.getMinValue())

                .maxValue(request.getMaxValue() != null && request.getMaxValue()!=0
                        ? request.getMaxValue() : entity.getMaxValue())

                .gender(request.getGender() != null ? request.getGender() : entity.getGender())
                .ageMin(request.getAgeMin() != null && request.getAgeMin() !=0
                        ? request.getAgeMin() : entity.getAgeMin())
                .ageMax(request.getAgeMax() !=null && request.getAgeMax() !=0
                        ?request.getAgeMax() : entity.getAgeMax())
                .pregnancyStatus(request.getPregnancyStatus()!=null ?
                        request.getPregnancyStatus() :entity.getPregnancyStatus())
                .unit(request.getUnit()!=null ?request.getUnit() :entity.getUnit())
                .build();


    }
}
