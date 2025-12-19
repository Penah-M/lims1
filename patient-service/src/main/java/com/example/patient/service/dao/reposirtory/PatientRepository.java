package com.example.patient.service.dao.reposirtory;

import com.example.patient.service.dao.entity.PatientEntity;
import com.lims.common.enums.Gender;
import com.lims.common.enums.PatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

   Optional<PatientEntity> findByFin(String fin);

   boolean existsByFin(String fin);

   Optional<PatientEntity> findByFinIgnoreCase(String fin);

   List<PatientEntity> findByUpdateAtBetween(
           LocalDateTime start,
           LocalDateTime end
   );

   List<PatientEntity> findByFirstNameIgnoreCase(String firstName);

   List<PatientEntity> findByLastNameIgnoreCase(String lastName);

   List<PatientEntity> findByFirstNameAndLastNameIgnoreCase(
           String firstName,
           String lastName
   );

   List<PatientEntity> findByGender(Gender gender);

   List<PatientEntity> findByStatus(PatientStatus status);
}

