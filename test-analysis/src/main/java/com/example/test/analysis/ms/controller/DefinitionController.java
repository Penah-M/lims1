package com.example.test.analysis.ms.controller;

import com.example.test.analysis.ms.dto.request.DefinitionRequest;
import com.example.test.analysis.ms.dto.request.UpdateDefinitionRequest;
import com.lims.common.enums.TestStatus;
import com.example.test.analysis.ms.service.DefinitionService;
import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import com.lims.common.enums.Unit;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/definition")

public class DefinitionController {

    DefinitionService definitionService;

    @PostMapping("/create")
    public DefinitionResponse createDefinition(@RequestBody @Valid DefinitionRequest request) {
        return definitionService.createDefinition(request);
    }

    @PutMapping("/{id}/update")
    public DefinitionResponse update(@PathVariable Long id,
                                     @RequestBody UpdateDefinitionRequest request) {
        return definitionService.update(id, request);
    }


    @PutMapping("/{id},price/{price}")
    public String changePrice(@PathVariable Long id,
                              @PathVariable BigDecimal price) {
        return definitionService.changePrice(id, price);
    }

    @PutMapping("/{id}/unit{unit}")
    public String changeUnit(@PathVariable Long id,
                             @PathVariable Unit unit) {
        return definitionService.changeUnit(id, unit);
    }

    @PutMapping("/{id}/status{status}")
    public String changeStatus(@PathVariable Long id,
                               @PathVariable TestStatus status) {
        return definitionService.changeStatus(id, status);
    }

    @GetMapping("/{id}/softDelete")
   public void softDelete(@PathVariable Long id){
        definitionService.softDelete(id);
    }
    @DeleteMapping("/{id}/hardDelete")
    public void hardDelete(@PathVariable Long id){
        definitionService.hardDelete(id);
    }

    @GetMapping("/{id}/find")
    public DefinitionResponse findId(@PathVariable Long id) {
        return definitionService.findId(id);
    }

    @GetMapping("/{shortCode}/findName")
    public DefinitionResponse findByName(@PathVariable String shortCode) {
        return definitionService.findByName(shortCode);
    }

    @GetMapping("/pagination")
    public Page<DefinitionResponse> getAllPagination(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id,asc") String[] sort) {
        return definitionService.getAllPagination(page, size, sort);
    }

    @GetMapping("/getAllActivePagination")
    public Page<DefinitionResponse> getAllActivePagination(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id,asc") String[] sort,
                                                           @RequestParam(required = false) TestStatus status) {
        return definitionService.getAllActivePagination(page, size, sort, status);
    }

    @GetMapping("/filter")
    public Page<DefinitionResponse> filterPagination(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id,asc")   String[] sort,
                                                     @RequestParam(required = false) TestStatus status,
                                                     @RequestParam(required = false) Long categoryId,
                                                     @RequestParam(required = false)   String categoryCode){
        return definitionService.filterPagination(page,size,sort,status,categoryId,categoryCode);
    }


    @PostMapping("/createAll")
    public void createDefinition(@RequestBody List<DefinitionRequest> request) {
        definitionService.createDefinitionAll(request);

    }
}
