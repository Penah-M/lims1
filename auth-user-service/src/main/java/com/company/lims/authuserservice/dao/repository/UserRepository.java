package com.company.lims.authuserservice.dao.repository;

import com.company.lims.authuserservice.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByUsername(String superUsername);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
