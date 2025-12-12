package com.example.order.ms.client;

import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "test-analysis",
        contextId = "definitionClient",
        url = "localhost:8083/api/v1/definition")
public interface DefinitionClient {

    @GetMapping("/{id}/find")
    DefinitionResponse findId(@PathVariable Long id);
}
