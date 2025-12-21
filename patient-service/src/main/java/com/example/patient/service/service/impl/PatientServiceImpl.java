package com.example.patient.service.service.impl;

import com.example.patient.service.dao.entity.PatientEntity;
import com.example.patient.service.dao.reposirtory.PatientRepository;
import com.example.patient.service.dto.request.PatientFilterRequest;
import com.example.patient.service.dto.request.PatientRequest;
import com.example.patient.service.exception.DuplicateException;
import com.example.patient.service.exception.PatientNotFoundException;
import com.example.patient.service.mapper.PatientMapper;
import com.example.patient.service.service.PatientService;
import com.example.patient.service.service.PatientSpecification;
import com.lims.common.dto.response.patient.PatientResponse;
import com.lims.common.enums.PatientStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientServiceImpl implements PatientService {
    PatientMapper patientMapper;
    PatientRepository patientRepository;
//    private final MapperBuilder mapperBuilder;

    public PatientResponse createPatient(PatientRequest request) {
        if (patientRepository.existsByFin(request.getFin())) {
            throw new DuplicateException("Bu FIN artıq qeydiyyatdan keçib");
        }
//        PatientStatus status = PatientStatus.ACTIVE;
//        if (request.getDocumentNumber() == null || request.getDocumentNumber().isEmpty()) {
//            status = PatientStatus.TEMPORARY;
//        }

        PatientEntity entity = patientMapper.entity(request);
        patientRepository.save(entity);
        log.info("Patient ugurla yaradildi");
        return patientMapper.response(entity);

    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {

        PatientEntity entity = patientRepository.findById(id).orElseThrow(() -> {
            log.warn("{} -ID e aid patient tapilmadi", id);
            return new PatientNotFoundException("Qeyd olunan Patient tapilmadi");
        });

        PatientEntity patientEntity = patientMapper.updateEntity(entity, request);
        patientRepository.save(patientEntity);
        log.info("Deyisiklik ugurla heyata kecirildi");
        return patientMapper.response(patientEntity);
    }


    public PatientResponse findById(Long id) {
        PatientEntity entity = patientRepository.findById(id).orElseThrow(() -> {
            log.warn("Qeyd olunan {}-IDli patient tapilmadi", id);
            return new PatientNotFoundException("Qeyd olunan xeste tapilmadi");
        });
        log.info("{}idli istifadeci haqqinda melumatlar gosterilir", id);
        PatientResponse response = patientMapper.response(entity);
        return response;

    }


    public Page<PatientResponse> getAllPagination(int page, int size, String[] sort) {

        String sortField = sort[0];
        String sortDirection = sort.length > 1 ? sort[1] : "asc";

        Sort sorting = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortField).descending() :
                Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sorting);

        return patientRepository.findAll(pageable).map(patientMapper::response);
    }




    public String deletePatient(Long id) {
        PatientEntity entity = patientRepository.findById(id).orElseThrow(() -> {
            log.warn("Qeyd olunan {} ID li patient tapilmadi", id);
            return new PatientNotFoundException("Qeyd olunan patient tapilmadi");
        });
        if (entity.getStatus() != com.lims.common.enums.PatientStatus.DELETE) {
            entity.setStatus(com.lims.common.enums.PatientStatus.DELETE);
            log.info("Qeyd olunan patient-in statusu DELETE oldu");
            patientRepository.save(entity);
            return "Qeyd olunan PATIENT-in statusu delete oldu";

        }
        log.warn("Qeyd etdiyiniz istifadecinin Statusu zaten DELETE-dir");
        return "Silmek istedyiniz patientin statusu DELETdir";
    }

    @Override
    public String restoreStatusPatient(Long id) {
        PatientEntity entity = patientRepository.findById(id).orElseThrow(() -> {
            log.warn("Qeyd olunan {} ID li patient tapilmadi..", id);
            return new PatientNotFoundException("Qeyd olunan patient tapilmadi");
        });
        if(entity.getStatus()== com.lims.common.enums.PatientStatus.DELETE){
            entity.setStatus(com.lims.common.enums.PatientStatus.ACTIVE);
            log.info("Qeyd etdyiniz PATIENT-in statusu deyisdirildi");
            patientRepository.save(entity);
            return "Qeyd etdyiniz PATIENT-in statusu deyisdirildi";
        }
        return "Qeyd etdiyiniz PATIENT-in satsu activdir";

    }

    public String hardDelete(Long id) {
        PatientEntity entity = patientRepository.findById(id).orElseThrow(() -> {
            log.warn("Qeyd olunan {} ID li patient tapilmadi.", id);
            return new PatientNotFoundException("Qeyd olunan patient tapilmadi");
        });
        log.warn("Qeyd olunan patient bazadan ugurla silindi");
        patientRepository.delete(entity);
        return "Qeyd olunan PATIENT bazada ugurla silindi";
    }

    public String changeStatus(Long id, PatientStatus newStatus){
        PatientEntity entity = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient tapilmadi: " + id)
                );
        if (entity.getStatus() == newStatus) {
            return "Status artiq " + newStatus;
        }
        entity.setStatus(newStatus);
        patientRepository.save(entity);
        return "Patient status  ugurla deyisdirildi: " + newStatus;
    }


    /// tarixe gore axtaris kommentlendi
//
//    public List<PatientResponse> searchByUpdateDate(LocalDate date) {
//        if (date == null) {
//            throw new IllegalArgumentException("Date cannot be null");
//        }
//        LocalDateTime startOfDay = date.atStartOfDay();
//        LocalDateTime endOfDay = date.atTime(MAX);
//
//        List<PatientEntity> byUpdateAtBetween = patientRepository.findByUpdateAtBetween(startOfDay, endOfDay);
//
//        List<PatientResponse> responses = new ArrayList<>();
//        for (PatientEntity entity : byUpdateAtBetween) {
//            PatientResponse response = patientMapper.response(entity);
//            responses.add(response);
//        }
//        if (responses.isEmpty()) {
//            log.warn("{} tarixe aid xeste melumatlari tapilmadi", date);
//            throw new PatientNotFoundException("Bu tarixdə heç bir xəstə tapılmadı: " + date);
//        }
//        log.info("{} qeyd etdiyiniz tarixdeki xesteler tapildi", date);
//        return responses;
//    }


// TODO Specification

//    public List<PatientResponse> betweenDate(LocalDate start,LocalDate end) {
//        if (start == null) {
//            throw new IllegalArgumentException("Date cannot be null");
//        }
//        LocalDateTime startTime=start.atStartOfDay();
//        LocalDateTime endTime;
//
//        if(end==null){
//            endTime=LocalDate.now().atTime(MAX);
//        }else{
//            endTime=end.atTime(MAX);
//        }
//        if(end!=null&&end.isBefore(start)){
//            throw new IllegalArgumentException("Bitmə tarixi başlanğıc tarixindən əvvəl ola bilməz");
//        }
//
//        List<PatientEntity> patientEntities= patientRepository.findByUpdateAtBetween(startTime, endTime);
//
//        if (patientEntities.isEmpty()) {
//            log.warn("Bu tarix intervalında xəstə tapılmadı: {} - {}", start, (end == null ? "now" : end));
//            throw new PatientNotFoundException("Bu tarix intervalında xəstə tapılmadı");
//        }
//        List<PatientResponse> responses = new ArrayList<>();
//        for (PatientEntity entity : patientEntities) {
//            PatientResponse response = patientMapper.response(entity);
//            responses.add(response);
//        }
//        log.info("Tarix aralığı: {} - {} üçün xəstələr tapıldı",
//                start, (end == null ? "now" : end));
//        return responses;
//    }



    ///  ada gore axtaris Specification  var deye silndi
//    public List<PatientResponse> searchByName(String name) {
//        List<PatientEntity> byFirstName = patientRepository.findByFirstNameIgnoreCase(name.trim());
//        List<PatientResponse> responses = new ArrayList<>();
//
//        for (PatientEntity entity : byFirstName) {
//            PatientResponse response = patientMapper.response(entity);
//            responses.add(response);
//        }
//        if (responses.isEmpty()) {
//            log.warn("Qeyd olunan {} adda istifadeci tapilmadi", name);
//            throw new PatientNotFoundException("Qeyd olunan xeste tapilmadi");
//        }
//        return responses;
//    }



    // TODO Specification
//    public List<PatientResponse> searchByLastName(String lastName) {
//        List<PatientEntity> byFirstName = patientRepository.findByLastNameIgnoreCase(lastName.trim());
//        List<PatientResponse> responses = new ArrayList<>();
//
//        for (PatientEntity entity : byFirstName) {
//            PatientResponse response = patientMapper.response(entity);
//            responses.add(response);
//        }
//        if (responses.isEmpty()) {
//            log.warn("Qeyd olunan {} soyadda istifadeci tapilmadi", lastName);
//            throw new PatientNotFoundException("Qeyd olunan xeste tapilmadi");
//        }
//        return responses;
//    }

    /// Specification  var deye
//    public List<PatientResponse> searchNameAndLastName(String name, String lastName) {
//
//        List<PatientResponse> responses = new ArrayList<>();
//        List<PatientEntity> entities =
//                patientRepository.findByFirstNameAndLastNameIgnoreCase(name, lastName);
//
//        for(PatientEntity entity:entities){
//            PatientResponse response = patientMapper.response(entity);
//            responses.add(response);
//        }

//        List<PatientResponse> patientResponses = searchByName(name);
//        List<PatientResponse> patientResponses1 = searchByLastName(lastName);
//
//
//
//        responses.addAll(patientResponses1);
//        responses.addAll(patientResponses);
//        return responses;
//    }

    public PatientResponse findByFin(String fin) {

        PatientEntity entity = patientRepository.findByFinIgnoreCase(fin).orElseThrow(() -> {
            log.warn(" Qeyd olunan: {}  Fine malik istifadeci tapilmad", fin);
            return new PatientNotFoundException("Qeyd etdiyiniz fine aid istifadeic tapilmadi");
        });
        PatientResponse response = patientMapper.response(entity);
        return response;
    }


    /// Specification
//    public List<PatientResponse> findGender(Gender gender) {
//        List<PatientEntity> byGender = patientRepository.findByGender(gender);
//        List<PatientResponse> responses = new ArrayList<>();
//
//        for (PatientEntity entity : byGender) {
//            PatientResponse response = patientMapper.response(entity);
//            responses.add(response);
//        }
//        if (responses.isEmpty()) {
//            throw new PatientNotFoundException("Bu cinsdə xəstə tapılmadı: " + gender);
//        }
//        return responses;
//
//    }


///  TODO  Specificationon

//    public List<PatientResponse> getStatus(PatientStatus status) {
//        List<PatientEntity> entities = patientRepository.findByStatus(status);
//
//        if (entities.isEmpty()) {
//            log.warn("Bu {} statuslu patient tapilmadi", status);
//            throw new PatientNotFoundException("Bu Statuslu Patient tapilmadi");
//        }
//
//        List<PatientResponse> responses = new ArrayList<>();
//        for (PatientEntity entity : entities) {
//            responses.add(patientMapper.response(entity));
//        }
//        return responses;
//    }

//    private String generateTemporaryFIN() {
//        int size = patientRepository.findByStatus(TEMPORARY).size();
//
//        long count = size+ 1;
//        return String.format("TEMP%04d", count);
//
////    yeni dogulan korpelerin fini olmadigi ucun metod avto fin generate eden metod yazmaq isteyirdim
//  }




    @Override
    public Page<PatientResponse> filterPatients(
            PatientFilterRequest filter,
            int page,
            int size,
            String[] sort
    ) {
        String sortField = sort[0];
        String direction = sort.length > 1 ? sort[1] : "asc";

        Pageable pageable = PageRequest.of(
                page,
                size,
                direction.equalsIgnoreCase("desc")
                        ? Sort.by(sortField).descending()
                        : Sort.by(sortField).ascending()
        );

        return patientRepository
                .findAll(PatientSpecification.filter(filter), pageable)
                .map(patientMapper::response);
    }

}
