package com.example.test.analysis.ms.dao.repository;

import com.example.test.analysis.ms.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
   Optional<CategoryEntity> findByName(String name);
}
