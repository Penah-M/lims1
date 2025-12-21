package com.example.patient.service.service;

import com.example.patient.service.dao.entity.PatientEntity;
import com.example.patient.service.dto.request.PatientFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalTime.MAX;

public class PatientSpecification {

    public static Specification<PatientEntity> filter(PatientFilterRequest f) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (f.getFirstName() != null && !f.getFirstName().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("firstName")),
                                "%" + f.getFirstName().toLowerCase() + "%"
                        )
                );
            }

            if (f.getLastName() != null && !f.getLastName().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("lastName")),
                                "%" + f.getLastName().toLowerCase() + "%"
                        )
                );
            }

            if (f.getFin() != null && !f.getFin().isBlank()) {
                predicates.add(
                        cb.equal(cb.lower(root.get("fin")), f.getFin().toLowerCase())
                );
            }

            if (f.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), f.getGender()));
            }

            if (f.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), f.getStatus()));
            }

            if (f.getBirthdayFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("birthday"),
                                f.getBirthdayFrom()
                        )
                );
            }

            if (f.getBirthdayTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("birthday"),
                                f.getBirthdayTo()
                        )
                );
            }

            if (f.getCreatedFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("createdAt"),
                                f.getCreatedFrom().atStartOfDay()
                        )
                );
            }

            if (f.getCreatedTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("createdAt"),
                                f.getCreatedTo().atTime(MAX)
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
