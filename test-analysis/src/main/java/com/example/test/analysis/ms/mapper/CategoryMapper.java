package com.example.test.analysis.ms.mapper;

import com.example.test.analysis.ms.dao.entity.CategoryEntity;
import com.example.test.analysis.ms.dto.request.CategoryRequest;
import com.example.test.analysis.ms.dto.request.UpdateCategoryRequest;
import com.example.test.analysis.ms.dto.response.CategoryResponse;
import com.example.test.analysis.ms.enums.TestStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper {
    public CategoryEntity entity(CategoryRequest request) {
        return CategoryEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .status(TestStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public CategoryResponse response(CategoryEntity entity){
        return CategoryResponse.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updateAt(entity.getUpdateAt())
                .id(entity.getId())
                .build();
    }
    public CategoryEntity updateEntity(CategoryEntity entity, UpdateCategoryRequest request){
        if (entity == null || request == null) {
            throw new IllegalArgumentException("Entity və Request null ola bilməz");
        }
       return entity.toBuilder()
               .name(request.getName())
               .code(request.getCode())
               .status(request.getStatus())
               .build();
    }
}
