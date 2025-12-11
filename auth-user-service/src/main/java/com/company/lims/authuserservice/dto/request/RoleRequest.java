package com.company.lims.authuserservice.dto.request;

import com.company.lims.authuserservice.enums.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class RoleRequest {

    @NotNull
    RoleName name;

    @NotNull
    String description;


}
