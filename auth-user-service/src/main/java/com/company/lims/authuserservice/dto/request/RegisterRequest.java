package com.company.lims.authuserservice.dto.request;


import com.company.lims.authuserservice.enums.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class RegisterRequest {
    @NotNull
    String userName;

    @NotNull
    String password;

    @Email
    @NotNull
    String email;

    @NotNull
    RoleName roleName;
}
