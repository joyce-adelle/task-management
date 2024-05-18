package com.example.taskmanagement.controllers;

import com.example.taskmanagement.dtos.requests.UpdateUserRoleRequest;
import com.example.taskmanagement.dtos.responses.AdminTaskResponse;
import com.example.taskmanagement.dtos.responses.ApiListResponse;
import com.example.taskmanagement.dtos.responses.ApiResponse;
import com.example.taskmanagement.dtos.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "Admin Endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public interface AdminController {

    @Operation(summary = "Change User Role", description = "Modify role of any user")
    ResponseEntity<ApiResponse<UserResponse>> changeUserRole(@RequestBody @Valid UpdateUserRoleRequest request,
                                                             HttpServletRequest httpServletRequest);

    @Operation(summary = "Get All Users", description = "Get all users, uses limit-offset pagination")
    ResponseEntity<ApiListResponse<List<UserResponse>>> getAllUsers(@RequestParam(defaultValue = "0") @Min(0) long offset, @RequestParam(defaultValue = "10") @Min(1) int limit,
                                                                    HttpServletRequest httpServletRequest);

    @Operation(summary = "Get A Task", description = "Get a task by id created by any user and includes information of user who created task")
    ResponseEntity<ApiResponse<AdminTaskResponse>> getTask(@PathVariable("id") @Min(1) @NotNull(message = "Task id is required") Integer id, HttpServletRequest httpServletRequest);

    @Operation(summary = "Get All Tasks", description = "Get all tasks created by all users and include information of user who created task, uses limit-offset pagination")
    ResponseEntity<ApiListResponse<List<AdminTaskResponse>>> getAllTasks(@RequestParam(defaultValue = "0") @Min(0) long offset, @RequestParam(defaultValue = "10") @Min(1) int limit,
                                                                         HttpServletRequest httpServletRequest);
}
