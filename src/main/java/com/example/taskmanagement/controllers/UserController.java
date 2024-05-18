package com.example.taskmanagement.controllers;

import com.example.taskmanagement.dtos.requests.UpdatePasswordRequest;
import com.example.taskmanagement.dtos.requests.UpdateUserRequest;
import com.example.taskmanagement.dtos.responses.ApiResponse;
import com.example.taskmanagement.dtos.responses.MessageResponse;
import com.example.taskmanagement.dtos.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "User Endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public interface UserController {

    @Operation(summary = "Update User Details", description = "Update user details (only name can be updated)")
    ResponseEntity<ApiResponse<UserResponse>> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest,
                                                         HttpServletRequest httpServletRequest);

    @Operation(summary = "Get User", description = "Get the current logged in user")
    ResponseEntity<ApiResponse<UserResponse>> getUser(HttpServletRequest httpServletRequest);

    @Operation(summary = "Update User Password", description = "Update user password for accessing the application")
    ResponseEntity<ApiResponse<MessageResponse>> updatePassword(@RequestBody @Valid UpdatePasswordRequest request,
                                                                HttpServletRequest httpServletRequest);

}
