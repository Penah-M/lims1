package com.example.test.analysis.ms.dao.repository;

import com.example.test.analysis.ms.dao.entity.DefinitionEntity;
import com.example.test.analysis.ms.enums.TestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefinitionRepository extends JpaRepository<DefinitionEntity, Long> {
    Optional<DefinitionEntity> findByCodeShort(String codeShort);
    Page<DefinitionEntity> findByStatus(TestStatus status, Pageable pageable);
    Page<DefinitionEntity> findByCategory_Id(Long id,Pageable pageable);
    Page<DefinitionEntity>findByCategory_Code(String categoryCode,Pageable pageable);
    Page<DefinitionEntity> findByStatusAndCategory_Id(TestStatus status,Long id, Pageable pageable);
    Page<DefinitionEntity> findByStatusAndCategory_Code(TestStatus status,String categoryCode,Pageable pageable);


}
