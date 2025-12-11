package com.example.order.ms.service;

import com.example.order.ms.client.PatientClient;
import com.example.order.ms.client.RangeClient;
import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import com.lims.common.enums.Gender;
import com.lims.common.enums.PregnancyStatus;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Service
public class YoxlamaService {
    PatientClient patientClient;
    RangeClient rangeClient;

    public PatientResponse yoxlama(Long id){
       return patientClient.findById(id);
    }


    public RangeResponse findBestRange(Long definitionId,  Gender gender,
            Integer age,
            PregnancyStatus pregnancyStatus){
        return rangeClient.findBestRange(definitionId,gender,age,pregnancyStatus);
    }


}
