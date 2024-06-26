package com.example.taskmanagement.controllers.implementations;

import com.example.taskmanagement.controllers.TaskController;
import com.example.taskmanagement.dtos.requests.CreateTaskRequest;
import com.example.taskmanagement.dtos.requests.UpdateTaskRequest;
import com.example.taskmanagement.dtos.responses.*;
import com.example.taskmanagement.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskControllerImplementation implements TaskController {

    private final TaskService taskService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@RequestBody @Valid CreateTaskRequest request, HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<TaskResponse>builder()
                .data(taskService.createTask(request))
                .isSuccessful(true)
                .status(HttpStatus.OK.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build());

    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable("id") @Min(1) @NotNull(message = "Task id is required") Integer id, @RequestBody @Valid UpdateTaskRequest request, HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<TaskResponse>builder()
                .data(taskService.updateTask(id, request))
                .isSuccessful(true)
                .status(HttpStatus.OK.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build());

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTask(@PathVariable("id") @Min(1) @NotNull(message = "Task id is required") Integer id, HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<TaskResponse>builder()
                .data(taskService.getTask(id))
                .isSuccessful(true)
                .status(HttpStatus.OK.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build());

    }

    @Override
    @GetMapping
    public ResponseEntity<ApiListResponse<List<TaskResponse>>> getTasks(@RequestParam(defaultValue = "0") @Min(0) long offset,
                                                                        @RequestParam(defaultValue = "10") @Min(1) int limit, HttpServletRequest httpServletRequest) {

        TasksResponse res = taskService.getUserTasks(offset, limit);
        return ResponseEntity.ok(ApiListResponse.<List<TaskResponse>>builder()
                .data(res.getTasks())
                .total(res.getTotal())
                .isSuccessful(true)
                .status(HttpStatus.OK.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build());

    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteTask(@PathVariable("id") @Min(1) @NotNull(message = "Task id is required") Integer id, HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<MessageResponse>builder()
                .data(taskService.deleteTask(id))
                .isSuccessful(true)
                .status(HttpStatus.OK.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build());

    }

}
