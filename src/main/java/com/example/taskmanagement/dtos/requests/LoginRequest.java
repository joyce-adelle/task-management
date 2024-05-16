package com.example.taskmanagement.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Input a valid Email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}
