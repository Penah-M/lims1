package com.example.order.ms.client;

import com.lims.common.dto.response.patient.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service",
        url = "http://localhost:8082/api/v1/patient")
public interface PatientClient {

    @GetMapping("{id}/findById")
    PatientResponse findById(@PathVariable Long id);
}
