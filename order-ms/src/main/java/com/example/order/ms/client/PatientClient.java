package com.example.order.ms.client;

import com.lims.common.dto.response.patient.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service",
        url =  "${patient.service.url}")
public interface PatientClient {

    @GetMapping("/patient/{id}/findById")
    PatientResponse findById(@PathVariable Long id);
}
