package com.example.taskmanagement.dtos.requests;

import com.example.taskmanagement.annotations.validations.enums.ValueOfEnum;
import com.example.taskmanagement.utils.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRoleRequest {

    @NotBlank(message = "user id to be updated is required")
    private String userId;

    @ValueOfEnum(enumClass = Role.class, checkWithUpperCase = true, message = "Role must be any of admin, or user")
    private String role;

}
