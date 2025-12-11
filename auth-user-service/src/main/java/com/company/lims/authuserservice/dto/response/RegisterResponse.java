package com.company.lims.authuserservice.dto.response;

import com.company.lims.authuserservice.enums.RoleName;
import com.company.lims.authuserservice.enums.UserStatus;
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
public class RegisterResponse {

    Long id;
    String userName;
    String email;
    UserStatus status;
    RoleName roleName;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
}
