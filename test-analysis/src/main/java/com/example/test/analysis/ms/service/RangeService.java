package com.example.test.analysis.ms.service;

import com.example.test.analysis.ms.dto.request.RangeRequest;
import com.example.test.analysis.ms.dto.request.UpdateRangeRequest;
import com.example.test.analysis.ms.dto.response.RangeResponse;
import com.example.test.analysis.ms.enums.Gender;
import com.example.test.analysis.ms.enums.PregnancyStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RangeService {

    RangeResponse create(Long definitionId, RangeRequest request);

    List<RangeResponse> getRange(Long definitionId);

    List<RangeResponse> getRangesByCode(String code);

    List<RangeResponse> getRangesByName(String name);

    List<RangeResponse> search(String all);

    RangeResponse update(Long id, UpdateRangeRequest request);
    String hardDelete(Long id);

    RangeResponse findById (Long id);

    Page<RangeResponse> getAllPagination(int page, int size, String[] sort);
    Page<RangeResponse> getAllDefinition(Long definitionId, int page, int size, String[] sort);

    Page<RangeResponse> getAllByShortCode(String shortCode, int page, int size,
                                                 String[] sort);

    RangeResponse findBestRange(Long definitionId, Gender gender,
                                       Integer age, PregnancyStatus pregnancyStatus);
}
