package com.example.taskmanagement.controllers.implementations;

import com.example.taskmanagement.controllers.UserController;
import com.example.taskmanagement.dtos.requests.UpdatePasswordRequest;
import com.example.taskmanagement.dtos.requests.UpdateUserRequest;
import com.example.taskmanagement.dtos.responses.ApiResponse;
import com.example.taskmanagement.dtos.responses.MessageResponse;
import com.example.taskmanagement.dtos.responses.UserResponse;
import com.example.taskmanagement.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserControllerImplementation implements UserController {

    private final UserService userService;

    @Override
    @PatchMapping
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest,
                                                                HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .data(userService.updateUser(updateUserRequest))
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build());

    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<UserResponse>> getUser(HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .data(userService.getUser())
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build());

    }

    @Override
    @PostMapping("/update-password")
    public ResponseEntity<ApiResponse<MessageResponse>> updatePassword(@RequestBody @Valid UpdatePasswordRequest request,
                                                                       HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<MessageResponse>builder()
                .status(HttpStatus.OK.value())
                .data(userService.updatePassword(request))
                .timeStamp(Instant.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build());

    }
}
