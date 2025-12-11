package com.company.lims.authuserservice.mapper;

import com.company.lims.authuserservice.dao.entity.RoleEntity;
import com.company.lims.authuserservice.dao.entity.UserEntity;
import com.company.lims.authuserservice.dto.request.RegisterRequest;
import com.company.lims.authuserservice.dto.response.RegisterResponse;
import com.company.lims.authuserservice.dto.response.UserResponse;
import com.company.lims.authuserservice.enums.RoleName;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserMapper {
    public UserEntity entity(RegisterRequest request) {
        return UserEntity.builder()
                .username(request.getUserName())
                .roles(new HashSet<>())
                .email(request.getEmail())
                .build();
    }

    public RegisterResponse response(UserEntity entity) {
        return RegisterResponse.builder()
                .id(entity.getId())
                .userName(entity.getUsername())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .roleName( getRoleName(entity) )
                .createdAt(entity.getCreatedAt())
                .updateAt(entity.getUpdateAt())
                .build();
    }
    private RoleName getRoleName(UserEntity entity) {
        return entity.getRoles()
                .stream()
                .findFirst()
                .map(RoleEntity::getName)
                .orElse(null);
    }


    public UserResponse toUserResponse(UserEntity entity) {
        return UserResponse.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .roles(entity.getRoles()
                        .stream()
                        .map(role -> role.getName().name())
                        .toList())
                .build();
    }

}
