package com.company.lims.authuserservice.dao.repository;

import com.company.lims.authuserservice.dao.entity.RoleEntity;
import com.company.lims.authuserservice.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName(RoleName name);
}
