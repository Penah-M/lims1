package com.example.order.ms.controller;

import com.example.order.ms.service.YoxlamaService;
import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import com.lims.common.enums.Gender;
import com.lims.common.enums.PregnancyStatus;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE,makeFinal = true)
public class YoxlamaController {


    YoxlamaService yoxlamaService;

        @GetMapping("{id}/get")
    public PatientResponse yoxlama(@PathVariable Long id){
        return yoxlamaService.yoxlama(id);
    }
    @GetMapping("/findRange")
    public RangeResponse findBestRange(
            @RequestParam Long definitionId,
            @RequestParam Gender gender,
            @RequestParam Integer age,
            @RequestParam PregnancyStatus pregnancyStatus){
            return yoxlamaService.findBestRange(definitionId,gender,age,pregnancyStatus);
    }
}
