package com.example.test.analysis.ms.dao.repository;

import com.example.test.analysis.ms.dao.entity.RangeEntity;
import com.example.test.analysis.ms.enums.Gender;
import com.example.test.analysis.ms.enums.PregnancyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RangeRepository extends JpaRepository<RangeEntity, Long> {
    List<RangeEntity> findAllByTestDefinitionId(Long definitionId);

    @Query("SELECT r FROM RangeEntity r WHERE r.testDefinition.codeShort = :code")
    List<RangeEntity> findByDefinitionCodeShort(String code);

    @Query("SELECT r FROM RangeEntity r WHERE r.testDefinition.name = :name")
    List<RangeEntity> findByDefinitionName(String name);


    @Query("""
                SELECT r FROM RangeEntity r
                JOIN r.testDefinition d
                WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :text, '%'))
                   OR LOWER(d.codeShort) LIKE LOWER(CONCAT('%', :text, '%'))
                   OR LOWER(d.codeLong) LIKE LOWER(CONCAT('%', :text, '%'))
            """)
    List<RangeEntity> searchByDefinitionFlexible(String text);

    Page<RangeEntity> findAllByTestDefinitionId(Long definitionId, Pageable pageable);

    Page<RangeEntity> findAllByTestDefinitionCodeShort(String shortCode, Pageable pageable);

    @Query("""
    SELECT r FROM RangeEntity r
    WHERE r.testDefinition.id = :definitionId
      AND (r.gender = :gender OR r.gender = 'BOTH')
      AND (r.pregnancyStatus = :pregnancyStatus OR r.pregnancyStatus IS NULL)
      AND :age BETWEEN r.ageMin AND r.ageMax
""")
    List<RangeEntity> findSuitableRanges(Long definitionId,
                                         Gender gender,
                                         PregnancyStatus pregnancyStatus,
                                         Integer age);
}
