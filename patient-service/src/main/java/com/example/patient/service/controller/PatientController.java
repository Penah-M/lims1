package com.example.patient.service.controller;

import com.example.patient.service.dto.request.PatientRequest;
import com.example.patient.service.service.PatientService;
import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.enums.Gender;
import com.lims.common.enums.PatientStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/patient")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PatientController {
    PatientService patientService;



    @PostMapping("create")
    public PatientResponse createPatient(@Valid @RequestBody PatientRequest request) {
        return patientService.createPatient(request);
    }

    @PutMapping("{id}/update")
    public PatientResponse updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequest request) {
        return patientService.updatePatient(id,request);
    }

    @GetMapping("{id}/findById")
    public PatientResponse findById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    @GetMapping("all")
    public List<PatientResponse> getAll(){
        return patientService.getAll();
    }
    @GetMapping("/pagination")
    public Page<PatientResponse> getAllPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        return patientService.getAllPagination(page, size, sort);
    }
    @GetMapping("{id}/hardDelete")
    public String hardDelete(@PathVariable Long id){
        return patientService.hardDelete(id);
    }

    @PatchMapping("softDelete/{id}")
    public String deletePatient(@PathVariable Long id){
        return patientService.deletePatient(id);
    }
    @PatchMapping("restoreStatus/{id}")
    public String restoreStatusPatient(@PathVariable Long id){
        return patientService.restoreStatusPatient(id);
    }


    @GetMapping("search/{date}")
    public List<PatientResponse> search(@PathVariable
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                        LocalDate date){
        return patientService.searchByUpdateDate(date);
    }
    @GetMapping("between/{start}/{end}")
    public List<PatientResponse> betweenDate(@PathVariable LocalDate start,@PathVariable LocalDate end){
        return patientService.betweenDate(start,end);
    }

    @GetMapping("searchByName/{name}")
    public List<PatientResponse> searchByName(@PathVariable String name){
        return patientService.searchByName(name);
    }


    @GetMapping("{lastname}/searchByLastName")
    public List<PatientResponse> searchByLastName(@PathVariable("lastname") String lastName){
        return patientService.searchByLastName(lastName);
    }

    @GetMapping("{name}/{lastName}/searchNameAndLastName")
    public List<PatientResponse> searchNameAndLastName(@Valid @PathVariable String name,
                                                       @PathVariable String lastName){
        return patientService.searchNameAndLastName(name,lastName);
    }

    @GetMapping("fin/{fin}")
    public PatientResponse findByFin(@PathVariable String fin){
        return patientService.findByFin(fin);
    }

    @GetMapping("/gender{gender}")
    public List<PatientResponse> findGender(@PathVariable Gender gender){
        return patientService.findGender(gender);
    }

    @GetMapping("getStatus/{status}")
    public List<PatientResponse> getStatus(@PathVariable PatientStatus status){
        return patientService.getStatus(status);
    }

}
