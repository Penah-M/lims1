package com.example.patient.service.service;

import com.example.patient.service.dto.request.PatientFilterRequest;
import com.example.patient.service.dto.request.PatientRequest;
import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.enums.PatientStatus;
import org.springframework.data.domain.Page;

public interface PatientService {

  Page<PatientResponse> filterPatients(PatientFilterRequest filter,int page,int size,String[] sort);
  PatientResponse createPatient(PatientRequest request);

    PatientResponse updatePatient(Long id, PatientRequest request);

    PatientResponse findById(Long id);


    Page<PatientResponse> getAllPagination(int page, int size, String[] sort);

    String hardDelete(Long id);

    String deletePatient(Long id);

    String restoreStatusPatient(Long id);

    String changeStatus(Long id, PatientStatus newStatus);

//    List<PatientResponse> searchByUpdateDate(LocalDate date);
//
//    List<PatientResponse> betweenDate(LocalDate start, LocalDate end);
//
//    List<PatientResponse> searchByLastName(String lastName);
//
//    List<PatientResponse> searchByName(String name);
//
//
//    List<PatientResponse> searchNameAndLastName(String name, String lastName);
//
    PatientResponse findByFin(String fin);
//
//    List<PatientResponse> findGender(Gender gender);
//   List<PatientResponse> getStatus(PatientStatus status);

}
