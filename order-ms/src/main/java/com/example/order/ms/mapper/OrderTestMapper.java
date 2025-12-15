package com.example.order.ms.mapper;

import com.example.order.ms.dao.entity.OrderTestEntity;
import com.example.order.ms.enums.OrderTestStatus;
import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderTestMapper {

    public OrderTestEntity toEntity(
            DefinitionResponse definition,
            RangeResponse range
    ) {

        if (definition == null || range == null) {
            throw new IllegalArgumentException("Definition ve Range null ola bilmez");
        }

        return OrderTestEntity.builder()
                .testDefinitionId(definition.getId())
                .testName(definition.getName())
                .testCode(definition.getCodeShort())
                .unit(definition.getUnit())
                .price(definition.getPrice())
                .minValue(BigDecimal.valueOf(range.getMinValue()))
                .maxValue(BigDecimal.valueOf(range.getMaxValue()))
                .status(OrderTestStatus.PENDING)
                .build();
    }

    // TODO [RESULT-MS]:
    // Result MS gelenden sonra result bu mapper-de set olunmayacaq
}