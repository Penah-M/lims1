package com.company.lims.authuserservice.dto.response;

import com.company.lims.authuserservice.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
@Builder
public class RoleResponse {
    Long id;

    RoleName name;

    String description;

    LocalDateTime createdAt;

    LocalDateTime updateAt;



}
