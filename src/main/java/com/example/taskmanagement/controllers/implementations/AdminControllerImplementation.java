package com.example.taskmanagement.controllers.implementations;

import com.example.taskmanagement.controllers.AdminController;
import com.example.taskmanagement.dtos.requests.UpdateUserRoleRequest;
import com.example.taskmanagement.dtos.responses.*;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/admin")
public class AdminControllerImplementation implements AdminController {

    private final UserService userService;

    private final TaskService taskService;

    @Override
    @PostMapping("/users/change-role")
    public ResponseEntity<ApiResponse<UserResponse>> changeUserRole(@RequestBody @Valid UpdateUserRoleRequest request,
                                                                    HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .data(userService.updateUserRole(request))
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build());

    }

    @Override
    @GetMapping("/users")
    public ResponseEntity<ApiListResponse<List<UserResponse>>> getAllUsers(@RequestParam(defaultValue = "0") @Min(0) long offset, @RequestParam(defaultValue = "10") @Min(1) int limit,
                                                                           HttpServletRequest httpServletRequest) {

        UsersResponse res = userService.getAllUsers(offset, limit);
        return ResponseEntity.ok(ApiListResponse.<List<UserResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(res.getUsers())
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .total(res.getTotal())
                .build());

    }

    @Override
    @GetMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<AdminTaskResponse>> getTask(@PathVariable("id") @Min(1) @NotNull(message = "Task id is required") Integer id, HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<AdminTaskResponse>builder()
                .status(HttpStatus.OK.value())
                .data(taskService.getTaskAdmin(id))
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build());

    }

    @Override
    @GetMapping("/tasks")
    public ResponseEntity<ApiListResponse<List<AdminTaskResponse>>> getAllTasks(@RequestParam(defaultValue = "0") @Min(0) long offset, @RequestParam(defaultValue = "10") @Min(1) int limit,
                                                                                HttpServletRequest httpServletRequest) {

        AdminTasksResponse res = taskService.getAllTasks(offset, limit);
        return ResponseEntity.ok(ApiListResponse.<List<AdminTaskResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(res.getTasks())
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .total(res.getTotal())
                .build());

    }

}
