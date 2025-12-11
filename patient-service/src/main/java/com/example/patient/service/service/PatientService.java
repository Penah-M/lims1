package com.example.patient.service.service;

import com.example.patient.service.dto.request.PatientRequest;
import com.example.patient.service.dto.response.PatientResponse;
import com.example.patient.service.enums.Gender;
import com.example.patient.service.enums.PatientStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {
    PatientResponse createPatient(PatientRequest request);

    PatientResponse updatePatient(Long id, PatientRequest request);

    PatientResponse findById(Long id);

    List<PatientResponse> getAll();

    Page<PatientResponse> getAllPagination(int page, int size, String[] sort);

    String hardDelete(Long id);

    String deletePatient(Long id);

    String restoreStatusPatient(Long id);

    List<PatientResponse> searchByUpdateDate(LocalDate date);

    List<PatientResponse> betweenDate(LocalDate start, LocalDate end);

    List<PatientResponse> searchByLastName(String lastName);

    List<PatientResponse> searchByName(String name);


    List<PatientResponse> searchNameAndLastName(String name, String lastName);

    PatientResponse findByFin(String fin);

    List<PatientResponse> findGender(Gender gender);
   List<PatientResponse> getStatus(PatientStatus status);

}
