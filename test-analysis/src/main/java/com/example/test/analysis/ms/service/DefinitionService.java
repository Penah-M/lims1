package com.example.test.analysis.ms.service;

import com.example.test.analysis.ms.dto.request.DefinitionRequest;
import com.example.test.analysis.ms.dto.request.UpdateDefinitionRequest;
import com.example.test.analysis.ms.dto.response.DefinitionResponse;
import com.example.test.analysis.ms.enums.TestStatus;
import com.example.test.analysis.ms.enums.Unit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DefinitionService {
    DefinitionResponse createDefinition(DefinitionRequest request);

    DefinitionResponse update(Long id, UpdateDefinitionRequest request);

    String changePrice(Long id, double price);

    String changeUnit(Long id, Unit unit);

    DefinitionResponse findId(Long id);

    Page<DefinitionResponse> getAllPagination(int page, int size, String[] sort);

    void createDefinitionAll(List<DefinitionRequest> request);

    String changeStatus(Long id, TestStatus status);

    DefinitionResponse findByName(String shortCode);

    Page<DefinitionResponse> getAllActivePagination(int page, int size, String[] sort, TestStatus status);

    Page<DefinitionResponse> filterPagination(int page,
                                              int size,
                                              String[] sort,
                                              TestStatus status,
                                              Long categoryId, // categoryId ilə filtr
                                              String categoryCode);

    void softDelete(Long id);
    void hardDelete(Long id);


}
