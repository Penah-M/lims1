package com.example.test.analysis.ms.service.impl;

import com.example.test.analysis.ms.dao.entity.DefinitionEntity;
import com.example.test.analysis.ms.dao.entity.RangeEntity;
import com.example.test.analysis.ms.dao.repository.DefinitionRepository;
import com.example.test.analysis.ms.dao.repository.RangeRepository;
import com.example.test.analysis.ms.dto.request.RangeRequest;
import com.example.test.analysis.ms.dto.request.UpdateRangeRequest;
import com.example.test.analysis.ms.dto.response.RangeResponse;
import com.example.test.analysis.ms.enums.Gender;
import com.example.test.analysis.ms.enums.PregnancyStatus;
import com.example.test.analysis.ms.exception.DefinitionNotFoundException;
import com.example.test.analysis.ms.exception.RangeNotFoundException;
import com.example.test.analysis.ms.mapper.RangeMapper;
import com.example.test.analysis.ms.service.RangeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RangeImpl implements RangeService {

    DefinitionRepository definitionRepository;
    RangeRepository rangeRepository;
    RangeMapper rangeMapper;

    public RangeResponse create(Long definitionId, RangeRequest request) {
        DefinitionEntity entity = findDefinitionEntity(definitionId);
        RangeEntity rangeEntity = rangeMapper.entity(request, entity);
        log.info("Normativler bazaya ugurla qeyd olundu.");
        rangeRepository.save(rangeEntity);
        return rangeMapper.response(rangeEntity);
    }

    public RangeResponse update(Long id, UpdateRangeRequest request){
        RangeEntity rangeEntity = findRangeEntity(id);
        log.info("Qeyd olunan normativler ugurla deyisdirildi");
        RangeEntity updateEntity = rangeMapper.updateEntity(rangeEntity, request);
        rangeRepository.save(updateEntity);
        return rangeMapper.response(updateEntity);
    }


    public Page<RangeResponse> getAllPagination(int page, int size, String[] sort) {
        Pageable pageable = pageable(page, size, sort);

        return rangeRepository.findAll(pageable).map(rangeMapper::response);
    }

    public Page<RangeResponse> getAllDefinition(Long definitionId, int page, int size, String[] sort) {
        findDefinitionEntity(definitionId);
        Pageable pageable = pageable(page, size, sort);

        Page<RangeResponse> pageResult = rangeRepository.findAllByTestDefinitionId(definitionId, pageable).
                map(rangeMapper::response);

        if(pageResult.isEmpty()){
            throw new RangeNotFoundException("Bu definition-a aid range yoxdur");
        }
        return pageResult;
    }

    public Page<RangeResponse> getAllByShortCode(String shortCode, int page, int size,
                                                 String[] sort) {

        mapListOrThrow(rangeRepository.findByDefinitionCodeShort(shortCode.toUpperCase()),
                "Bu test koduna aid melumat tapilmadi: " + shortCode);
        Pageable pageable = pageable(page, size, sort);

        Page<RangeResponse> pageResult = rangeRepository.findAllByTestDefinitionCodeShort(shortCode.toUpperCase(),
                pageable).map(rangeMapper::response);

        if(pageResult.isEmpty()){
            log.info("Bu Teste-a aid Normativler yoxdur");
            throw new RangeNotFoundException("Bu Teste-a aid Normativler yoxdur");
        }
        return pageResult;
    }


    public String hardDelete(Long id){
        RangeEntity rangeEntity = findRangeEntity(id);
        log.info("Qeyd olunan {} ide aid normativ bazadan silindi",id);
        rangeRepository.delete(rangeEntity);
        return "Qeyd olunan normativ bazadan ugurla silindi";
    }
    public RangeResponse findById (Long id){
        log.warn("Qeyd olunan id-e aid melumatlar gosterilir..");
        return rangeMapper.response(findRangeEntity(id));
    }


    public List<RangeResponse> getRange(Long definitionId) {
        List<RangeEntity> entities = rangeRepository.findAllByTestDefinitionId(definitionId);
        return mapListOrThrow(entities, "Bu id-e aid melumat tapilmadi: " + definitionId);
    }

    public List<RangeResponse> getRangesByCode(String code) {
        return mapListOrThrow(rangeRepository.findByDefinitionCodeShort(code.toUpperCase()),
                "Bu test koduna aid melumat tapilmadi: " + code);
    }

    public List<RangeResponse> search(String all) {
        return mapListOrThrow(
                rangeRepository.searchByDefinitionFlexible(all),
                "Axtardiginiz melumat tapilmadi: " + all
        );
    }

    public List<RangeResponse> getRangesByName(String name) {
        return mapListOrThrow(
                rangeRepository.findByDefinitionName(name),
                "Bu ada malik test tapilmadi: " + name
        );
    }

    public RangeResponse findBestRange(Long definitionId, Gender gender,
                                          Integer age, PregnancyStatus pregnancyStatus) {

        List<RangeEntity> list = rangeRepository.findSuitableRanges(definitionId, gender, pregnancyStatus, age);

        if(list.isEmpty()) {
            throw new RangeNotFoundException("Bu xəstə məlumatlarına uyğun range tapılmadı");
        }

        // Normalda yalnız 1 dənə uyğun range olur
        return rangeMapper.response(list.get(0));
    }


    private DefinitionEntity findDefinitionEntity(Long definitionId) {
        DefinitionEntity entity = definitionRepository.findById(definitionId).orElseThrow(() -> {
            log.warn("Qeyd olunan {}-id-e aid melumat tapilmadi", definitionId);
            return new DefinitionNotFoundException("Id e aid melumat tapilmadi");
        });
        return entity;
    }

    private RangeEntity findRangeEntity(Long rangeId){
        RangeEntity rangeEntity = rangeRepository.findById(rangeId).orElseThrow(() -> {
            log.warn("melumat tapilmadi");
            return new RangeNotFoundException("Qeyd olunan melumat tapilmadi");
        });
        return rangeEntity;
    }

    private List<RangeResponse> mapListOrThrow(List<RangeEntity> entities, String message) {
        if (entities.isEmpty()) {
            log.warn(message);
            throw new DefinitionNotFoundException(message);
        }
        return entities.stream()
                .map(rangeMapper::response)
                .toList();
    }

    private Pageable pageable(int page, int size, String[] sort) {
        String sortField = "id";  // default
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
