package com.example.test.analysis.ms.service.impl;

import com.example.test.analysis.ms.dao.entity.CategoryEntity;
import com.example.test.analysis.ms.dao.entity.DefinitionEntity;
import com.example.test.analysis.ms.dao.repository.CategoryRepository;
import com.example.test.analysis.ms.dao.repository.DefinitionRepository;
import com.example.test.analysis.ms.dto.request.DefinitionRequest;
import com.example.test.analysis.ms.dto.request.UpdateDefinitionRequest;
import com.lims.common.enums.TestStatus;
import com.example.test.analysis.ms.exception.AlreadyExistsException;
import com.example.test.analysis.ms.exception.AnalysisCategoryNotFoundException;
import com.example.test.analysis.ms.exception.DefinitionNotFoundException;
import com.example.test.analysis.ms.exception.UnitAlreadyExistsException;
import com.example.test.analysis.ms.mapper.DefinitionMapper;
import com.example.test.analysis.ms.service.DefinitionService;
import com.lims.common.dto.response.test_analysis.DefinitionResponse;
import com.lims.common.enums.Unit;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DefinitionImpl implements DefinitionService {
    DefinitionRepository definitionRepository;
    DefinitionMapper mapper;
    CategoryRepository categoryRepository;

    public DefinitionResponse createDefinition(DefinitionRequest request) {

        CategoryEntity category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> {
            log.warn("Bu {} ide sahib kateqoriya tapilmadi.", request.getCategoryId());
            return new AnalysisCategoryNotFoundException("Qeyd olunan caterqoriya tapilmadi");
        });

        DefinitionEntity entity = mapper.entity(request);
        entity.setCategory(category);
        definitionRepository.save(entity);
        return mapper.response(entity);
    }

    // List formasinda 1den artiq melumatlar elave etmek ucun yazmisam (demo u cun)

    public void createDefinitionAll(List<DefinitionRequest> request) {
        for (DefinitionRequest request1 : request) {
            CategoryEntity category = categoryRepository.findById(request1.getCategoryId()).orElseThrow(() -> {
                log.warn("Bu {} ide sahib kateqoriya tapilmadi.", request1.getCategoryId());
                return new AnalysisCategoryNotFoundException("Qeyd olunan caterqoriya tapilmadi");
            });
            DefinitionEntity entity = mapper.entity(request1);
            entity.setCategory(category);
            definitionRepository.save(entity);
        }
    }
    public DefinitionResponse update(Long id, UpdateDefinitionRequest request) {
        DefinitionEntity entity = findEntity(id);
        CategoryEntity category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> {
            log.warn("Bu {} ide sahib kateqoriya tapilmadi.", request.getCategoryId());
            return new AnalysisCategoryNotFoundException("Qeyd olunan caterqoriya tapilmadi");
        });
        DefinitionEntity definitionEntity = mapper.updateEntity(entity, request);

        definitionEntity.setCategory(category);
        log.info("Qeyd olunan deyisiklikler qeyde alindi");
        definitionRepository.save(definitionEntity);
        return mapper.response(definitionEntity);
    }

    public String changePrice(Long id, BigDecimal price) {
        DefinitionEntity entity = findEntity(id);
        entity.setPrice(price);
        log.info("Qiymet ugurla deyisdirildi");
        definitionRepository.save(entity);
        return "Testin qiymeti ugurla deyisdirildi " + price;
    }

    public String changeUnit(Long id, Unit unit) {
        DefinitionEntity entity = findEntity(id);
        if (entity.getUnit().equals(unit)) {

            log.warn("Evvelki  {} olcu vahidi ile indiki {} olcu vahidi eynidir", unit, unit);
            throw new UnitAlreadyExistsException("Yeni olcu vahidi ile evvelki olcu vahidi eynidir.. ");
        }
        entity.setUnit(unit);
        definitionRepository.save(entity);
        return "Qeyd etdiyiniz olcu vahidi ugurla deyisdirildi :" + unit;

    }

    public String changeStatus(Long id, TestStatus status) {
        DefinitionEntity entity = findEntity(id);

        if (entity.getStatus().equals(status)) {
            log.warn("Evvelki  {} Status ile indiki {} status eynidir", status, status);
            throw new AlreadyExistsException("Yeni status ile evvelki status eynidir.. ");
        }
        entity.setStatus(status);
        definitionRepository.save(entity);
        return "Qeyd etdiyiniz testin Statusu deyisdirildi" + status;
    }

    public DefinitionResponse findId(Long id) {
        DefinitionEntity entity = findEntity(id);
        log.info("Qeyd etdiyiniz {} id e aid melumatlar gosterilir", id);
        return mapper.response(entity);

    }
    public DefinitionResponse findByName(String shortCode){
        DefinitionEntity entity = definitionRepository.findByCodeShort(shortCode.toUpperCase()).orElseThrow(() -> {
            log.warn("BU adda{} test tapilmadi", shortCode);
            return new DefinitionNotFoundException("Qeyd olunan test tapilmadi");
        });
        return mapper.response(entity);
    }

    public Page<DefinitionResponse> getAllPagination(int page, int size, String[] sort) {

        Pageable pageable = pageable(page, size, sort);

        return definitionRepository.findAll(pageable).map(mapper::response);
    }

    public Page<DefinitionResponse> getAllActivePagination(int page, int size,
                                                           String[] sort, TestStatus status) {
        Pageable pageable = pageable(page, size, sort);

        if (status == null) {
            return definitionRepository.findAll(pageable).map(mapper::response);
        } else {
            return definitionRepository.findByStatus(status,pageable).map(mapper::response);
        }
    }
    public Page<DefinitionResponse> filterPagination(int page,
                                                     int size,
                                                     String[] sort,
                                                     TestStatus status,
                                                     Long categoryId,
                                                     String categoryCode) {
        Pageable pageable = pageable(page, size, sort);

        if (status == null) {
            if (categoryId != null) {
                return definitionRepository.findByCategory_Id(categoryId, pageable).map(mapper::response);
            } else if (categoryCode != null) {
                return definitionRepository.findByCategory_Code(categoryCode, pageable).map(mapper::response);
            } else {
                return definitionRepository.findAll(pageable).map(mapper::response);
            }
        } else {
            if (categoryId != null) {
                return definitionRepository.findByStatusAndCategory_Id(status, categoryId, pageable)
                        .map(mapper::response);
            } else if (categoryCode != null) {
                return definitionRepository.findByStatusAndCategory_Code(status, categoryCode, pageable)
                        .map(mapper::response);
            } else {
                return definitionRepository.findByStatus(status, pageable).map(mapper::response);
            }
        }
    }

    public void softDelete(Long id){
        DefinitionEntity entity = findEntity(id);
        log.info("Qeyd olunan {} id-e aid {} test silindi(soft)",id,entity.getName());
        if(entity.getStatus().equals(TestStatus.DELETE)){
            log.warn("Qeyd etdiyiniz {} ide aid {} test zaten DELETE olub",id,entity.getName());
            throw new AlreadyExistsException("Qeyd etdiyiniz ide aid test zaten DELETE olub "+"ID: "+id+" Test Adi: "
                    +entity.getName());
        }
        entity.setStatus(TestStatus.DELETE);
        definitionRepository.save(entity);
    }

    public void hardDelete(Long id){
        DefinitionEntity entity = findEntity(id);
        log.warn("Qeyd olunan {} test ugurla bazadan silindi",entity.getName());
        definitionRepository.delete(entity);
    }


    private DefinitionEntity findEntity(Long id) {
        DefinitionEntity entity = definitionRepository.findById(id).orElseThrow(() -> {
            log.warn("Qeyd olunan {}-id-e aid melumat tapilmadi", id);
            return new DefinitionNotFoundException("Id e aid melumat tapilmadi");
        });
        return entity;
    }

    private Pageable pageable(int page, int size, String[] sort) {
        String sortField = "id";
        String sortDirection = "asc";

        if (sort != null && sort.length > 0) {
            sortField = sort[0];
            if (sort.length > 1) {
                sortDirection = sort[1];
            }
        }
        Sort sorting = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortField).descending() :
                Sort.by(sortField).ascending();
        return PageRequest.of(page, size, sorting);
    }
}
