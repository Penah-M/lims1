package com.example.test.analysis.ms.service.impl;

import com.example.test.analysis.ms.dao.entity.CategoryEntity;
import com.example.test.analysis.ms.dao.repository.CategoryRepository;
import com.example.test.analysis.ms.dto.request.CategoryRequest;
import com.example.test.analysis.ms.dto.request.UpdateCategoryRequest;
import com.example.test.analysis.ms.dto.response.CategoryResponse;
import com.lims.common.enums.TestStatus;
import com.example.test.analysis.ms.exception.AnalysisCategoryNotFoundException;
import com.example.test.analysis.ms.exception.DuplicateException;
import com.example.test.analysis.ms.exception.StatusAlreadyExistsException;
import com.example.test.analysis.ms.mapper.CategoryMapper;
import com.example.test.analysis.ms.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CategoryImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper mapper;

    public CategoryResponse createCategory(CategoryRequest request) {
        boolean present = categoryRepository.findByName(request.getName()).isPresent();
        if (present) {
            log.warn("{} qeyd etdiyiniz Kateqoriya artiq movcuddur", request.getName());
            throw new DuplicateException("Bu adda kateqoriya movcuddur");
        }
        log.info("Yeni yaradilan kateqoriyqa bazaya elave olundu");
        CategoryEntity entity = mapper.entity(request);
        categoryRepository.save(entity);
        return mapper.response(entity);
    }

    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(() -> {
            log.warn("Bu {} ide sahib kateqoriya tapilmadi", id);
            return new AnalysisCategoryNotFoundException("Qeyd olunan caterqoriya tapilmadi");
        });
        CategoryEntity entity1 = mapper.updateEntity(entity, request);
        categoryRepository.save(entity1);
        log.info("Deyisiklikler bazaya qeyd olundu");
        return mapper.response(entity1);
    }

    public void deleteCategory(Long id) {
//        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(() -> {
//            log.warn("Bu {} ide sahib kateqoriya tapilmadi.", id);
//            return new AnalysisCategoryNotFoundException("Qeyd olunan caterqoriya tapilmadi");
//        });
//        log.info("qeyd olunan kateqoriya bazadan ugurla silindi");
//        categoryRepository.delete(entity);
        CategoryEntity entity = entity(id);
        categoryRepository.delete(entity);


    }


    public CategoryResponse findById(Long id) {
//        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(() -> {
//            log.warn("Bu {} ide sahib kateqoriya tapilmadi..", id);
//            return new AnalysisCategoryNotFoundException("Qeyd olunan caterqoriya tapilmadi");
//        });
//        return mapper.response(entity);
        CategoryEntity entity = entity(id);
        return mapper.response(entity);

    }


    public String changeStatus(Long id, TestStatus status) {
        CategoryEntity entity = entity(id);
        if (entity.getStatus().equals(status)) {
            log.warn("Statusunu deyismek istedyiniz kateqoriya zaten {}-dir", status);
            throw new StatusAlreadyExistsException("Deyismek istdeiyiniz status zaten evvelki ile eynidir");
        }
        entity.setStatus(status);
        categoryRepository.save(entity);
        log.info("Status ugurla deyisdirildi");
        return "Status ugurla deyisdirildi";
    }

    public List<CategoryResponse> getAll() {
        List<CategoryResponse> responses = new ArrayList<>();
        List<CategoryEntity> all = categoryRepository.findAll();
        for (CategoryEntity entity : all) {
            CategoryResponse response = mapper.response(entity);
            responses.add(response);
        }

        return responses;
    }


    private CategoryEntity entity(Long id) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(() -> {
            log.warn("Bu {} ide sahib kateqoriya tapilmadi.", id);
            return new AnalysisCategoryNotFoundException("Qeyd olunan caterqoriya tapilmadi");
        });
        return entity;
    }


}
