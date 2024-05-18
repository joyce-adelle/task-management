package com.example.taskmanagement.controllers;

import com.example.taskmanagement.dtos.requests.CreateTaskRequest;
import com.example.taskmanagement.dtos.requests.UpdateTaskRequest;
import com.example.taskmanagement.dtos.responses.ApiListResponse;
import com.example.taskmanagement.dtos.responses.ApiResponse;
import com.example.taskmanagement.dtos.responses.MessageResponse;
import com.example.taskmanagement.dtos.responses.TaskResponse;
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

@Tag(name = "Task", description = "Task Endpoints")
@SecurityRequirement(name="Bearer Authentication")
public interface TaskController {

    @Operation(summary = "Create A Task", description = "Create a new task")
    ResponseEntity<ApiResponse<TaskResponse>> createTask(@RequestBody @Valid CreateTaskRequest request, HttpServletRequest httpServletRequest);

    @Operation(summary = "Update A Task", description = "Update details of a task by id created by current user")
    ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable("id") @Min(1) @NotNull(message = "Task id is required") Integer id, @RequestBody @Valid UpdateTaskRequest request, HttpServletRequest httpServletRequest);

    @Operation(summary = "Get A Task", description = "Get a task by id created by current user")
    ResponseEntity<ApiResponse<TaskResponse>> getTask(@PathVariable("id") @Min(1) @NotNull(message = "Task id is required") Integer id, HttpServletRequest httpServletRequest);

    @Operation(summary = "Get All Tasks", description = "Get all tasks created by current user, uses limit-offset pagination")
    ResponseEntity<ApiListResponse<List<TaskResponse>>> getTasks(@RequestParam(defaultValue = "0") @Min(0) long offset,
                                                                 @RequestParam(defaultValue = "10") @Min(1) int limit, HttpServletRequest httpServletRequest);

    @Operation(summary = "Delete A Task", description = "Delete a task by id created by current user")
    ResponseEntity<ApiResponse<MessageResponse>> deleteTask(@PathVariable("id") @Min(1) @NotNull(message = "Task id is required") Integer id, HttpServletRequest httpServletRequest);

}
