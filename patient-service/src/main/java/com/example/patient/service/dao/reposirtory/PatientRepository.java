package com.example.patient.service.dao.reposirtory;

import com.example.patient.service.dao.entity.PatientEntity;
import com.example.patient.service.enums.Gender;
import com.example.patient.service.enums.PatientStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PatientRepository  extends JpaRepository<PatientEntity,Long> {
   Optional<PatientEntity> findByDocumentNumber(String documentNumber);
//   List<PatientEntity> findByUpdateAt(LocalDate updateAt);
   List<PatientEntity> findByUpdateAtBetween(LocalDateTime start, LocalDateTime end);


   List<PatientEntity> findByFirstNameIgnoreCase(String firstName);
   List<PatientEntity> findByLastNameIgnoreCase(String lastName);
   List<PatientEntity> findByFirstNameAndLastNameIgnoreCase(String firstName,String lastName);
   List<PatientEntity> findByGender(Gender gender);

   boolean existsByDocumentNumber(@NotBlank(message = "FIN cannot be empty") String documentNumber);


   Optional<PatientEntity> findByDocumentNumberIgnoreCase(String documentNumber);

   List<PatientEntity> findByStatus(PatientStatus status);
}
