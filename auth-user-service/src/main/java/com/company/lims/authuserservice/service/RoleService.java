package com.company.lims.authuserservice.service;

import com.company.lims.authuserservice.dao.entity.RoleEntity;
import com.company.lims.authuserservice.dao.repository.RoleRepository;
import com.company.lims.authuserservice.dto.request.RoleRequest;
import com.company.lims.authuserservice.dto.response.RoleResponse;
import com.company.lims.authuserservice.enums.RoleName;
import com.company.lims.authuserservice.exception.RoleAlreadyExistsException;
import com.company.lims.authuserservice.exception.RoleNotFounException;
import com.company.lims.authuserservice.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository repository;
    RoleMapper roleMapper;


    public RoleResponse createRole(RoleRequest request) {
        RoleEntity entity = roleMapper.entity(request);
               repository.findByName(request.getName()).ifPresent(it -> {
            log.warn("Qeyd etdiyiniz {} rol artıq bazada mövcuddur", request.getName());
            throw   new RoleAlreadyExistsException("Rol artiq bazada movcuddur");
        });
        repository.save(entity);
        log.info("Yeni {} rol yaradildi", request.getName());
        return roleMapper.response(entity);
    }

    public String deleteRole(Long id) {
        RoleEntity roleEntity = repository.findById(id).orElseThrow(() -> {
            log.error("Qeyd olunan rol tapilmadi");
            return new RoleNotFounException("Qeyd olunan rol tapilmadi");
        });
        repository.delete(roleEntity);
        log.info("Qeyd olunan rol ugurla silindi");
        return "Rol uğurla silindi";
    }

    public List<RoleResponse> getAll() {
        List<RoleEntity> entities = repository.findAll();
        List<RoleResponse> roleResponses = new ArrayList<>();

        for (RoleEntity entity : entities) {
            RoleResponse response = roleMapper.response(entity);
            roleResponses.add(response);
        }
        log.info("Butun rollar gosterilir..");
        return roleResponses;
    }

    public void deleteRole(RoleName name) {
        RoleEntity entity = repository.findByName(name).orElseThrow(() -> {
            log.error("Silmek istediyiniz {} rol Bazada yoxdur", name);
            return new RoleNotFounException("Qeyd etdiyiniz Role bazada yoxdur");
        });
        log.info("Qeyd olunan rol ugurla bazadan silindi");
        repository.delete(entity);
    }
}
