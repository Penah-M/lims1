package com.company.lims.authuserservice.mapper;

import com.company.lims.authuserservice.dao.entity.RoleEntity;
import com.company.lims.authuserservice.dto.request.RoleRequest;
import com.company.lims.authuserservice.dto.response.RoleResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RoleMapper {
    public RoleEntity entity(RoleRequest request) {
        return RoleEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public RoleResponse response(RoleEntity entity) {
        return RoleResponse.builder()
                .id(entity.getId())
                .createdAt(LocalDateTime.now())
                .description(entity.getDescription())
                .name(entity.getName())
                .updateAt(LocalDateTime.now())
                .build();
    }
}
