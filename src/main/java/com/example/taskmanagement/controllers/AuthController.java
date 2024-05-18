package com.example.taskmanagement.controllers;

import com.example.taskmanagement.dtos.requests.LoginRequest;
import com.example.taskmanagement.dtos.requests.SignUpRequest;
import com.example.taskmanagement.dtos.responses.ApiResponse;
import com.example.taskmanagement.dtos.responses.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Authentication Endpoints")
public interface AuthController {

    @Operation(summary = "Sign Up User", description = "Creates and Login user into application and returns jwt token to be used for further queries")
    ResponseEntity<ApiResponse<LoginResponse>> signUp(@RequestBody @Valid SignUpRequest request, HttpServletRequest httpServletRequest);

    @Operation(summary = "Login User", description = "Login user into application and returns jwt token to be used for further queries")
    ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request, HttpServletRequest httpServletRequest);
}
