package com.example.test.analysis.ms.service;

import com.example.test.analysis.ms.dto.request.CategoryRequest;
import com.example.test.analysis.ms.dto.request.UpdateCategoryRequest;
import com.example.test.analysis.ms.dto.response.CategoryResponse;
import com.example.test.analysis.ms.enums.TestStatus;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse findById(Long id);

    String changeStatus(Long id, TestStatus status);

    List<CategoryResponse> getAll();


}
