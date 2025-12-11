package com.example.test.analysis.ms.controller;

import com.example.test.analysis.ms.dto.request.CategoryRequest;
import com.example.test.analysis.ms.dto.request.UpdateCategoryRequest;
import com.example.test.analysis.ms.dto.response.CategoryResponse;
import com.example.test.analysis.ms.enums.TestStatus;
import com.example.test.analysis.ms.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/create")
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }


    @PutMapping("/{id}/update")
    public CategoryResponse updateCategory(@PathVariable Long id,
                                           @RequestBody UpdateCategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}/find")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PatchMapping("/{id}/{status}/changeStatus")
    public String changeStatus(@PathVariable Long id,
                               @PathVariable  TestStatus status) {
        return categoryService.changeStatus(id, status);
    }

    @GetMapping("/getAll")
    public List<CategoryResponse> getAll(){
        return categoryService.getAll();
    }
}
