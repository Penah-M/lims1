package com.example.order.ms.client;

import com.lims.common.dto.response.test_analysis.RangeResponse;
import com.lims.common.enums.Gender;
import com.lims.common.enums.PregnancyStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "test-analysis",
        contextId = "rangeClient",
        url = "${test.service.url}"
)
public interface RangeClient {
    @GetMapping("/range/findBestRange")
     RangeResponse findBestRange(
            @RequestParam Long definitionId,
            @RequestParam Gender gender,
            @RequestParam Integer age,
            @RequestParam PregnancyStatus pregnancyStatus);
}
