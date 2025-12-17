package com.example.order.ms.domain.factory;

import com.example.order.ms.dao.entity.OrderTestEntity;
import com.example.order.ms.enums.OrderTestStatus;
import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderTestCreator {

    public OrderTestEntity create(
            DefinitionResponse definition,
            RangeResponse range
    ) {
        OrderTestEntity test = new OrderTestEntity();

        test.setTestDefinitionId(definition.getId());
        test.setTestName(definition.getName());
        test.setUnit(definition.getUnit());
        test.setPrice(definition.getPrice());

        test.setMinValue(BigDecimal.valueOf(range.getMinValue()));
        test.setMaxValue(BigDecimal.valueOf(range.getMaxValue()));
        test.setStatus(OrderTestStatus.PENDING);

        return test;
    }
}
