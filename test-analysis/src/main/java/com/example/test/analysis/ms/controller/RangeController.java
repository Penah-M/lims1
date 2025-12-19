package com.example.test.analysis.ms.controller;

import com.example.test.analysis.ms.dto.request.RangeRequest;
import com.example.test.analysis.ms.dto.request.UpdateRangeRequest;
import com.example.test.analysis.ms.service.RangeService;
import com.lims.common.dto.response.test_analysis.RangeResponse;
import com.lims.common.enums.Gender;
import com.lims.common.enums.PregnancyStatus;
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

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE,makeFinal = true)
@RequestMapping("/api/v1/range")
public class RangeController {
    RangeService rangeService;

    @PostMapping("/{definitionId}/create")
    public RangeResponse create(@PathVariable Long definitionId,
                                @RequestBody RangeRequest request){
        return rangeService.create(definitionId,request);
    }
    @PutMapping("/{id}/update")
    public RangeResponse update(@PathVariable Long id,
                                @RequestBody UpdateRangeRequest request){
        return rangeService.update(id,request);
    }


    @GetMapping("/{definitionId}")
    public List<RangeResponse> getRange(@PathVariable Long definitionId){
        return rangeService.getRange(definitionId);
    }

    @GetMapping("/{code}/byCode")
    public  List <RangeResponse> getRangesByCode(@PathVariable String code){
        return rangeService.getRangesByCode(code);
    }

    @GetMapping("/{name}/byName")
    public  List <RangeResponse> getRangesByName(@PathVariable String name){
        return rangeService.getRangesByName(name);
    }

    @GetMapping("/{all}/search")
    public List<RangeResponse> search(@PathVariable String all){
        return rangeService.search(all);
    }

    @DeleteMapping("/{id}/hardDelete")
    public String hardDelete(@PathVariable Long id){
        return  rangeService.hardDelete(id);
    }

    @GetMapping("/{id}/find")
    public RangeResponse findById (@PathVariable Long id){
        return rangeService.findById(id);
    }

    @GetMapping("/pagination")
    public Page<RangeResponse> getAllPagination(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id,asc") String[] sort){
        return rangeService.getAllPagination(page,size,sort);
    }

    @GetMapping("/definition/{definitionId}/pagination")
    public Page<RangeResponse> getAllDefinition(@PathVariable Long definitionId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id,asc") String[] sort){
        return rangeService.getAllDefinition(definitionId,page,size,sort);
    }

    @GetMapping("/code/{shortCode}/pagination")
    public Page<RangeResponse> getAllByShortCode(@PathVariable String shortCode,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam( defaultValue = "id,asc") String[] sort){
        return rangeService.getAllByShortCode(shortCode,page,size,sort);
    }

    @GetMapping("/findBestRange")
    public RangeResponse findBestRange(
            @RequestParam Long definitionId,
            @RequestParam Gender gender,
            @RequestParam Integer age,
            @RequestParam PregnancyStatus pregnancyStatus) {

        return rangeService.findBestRange(definitionId, gender, age, pregnancyStatus);
    }


}
