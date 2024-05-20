package com.example.taskmanagement.controllers.implementations;

import com.example.taskmanagement.controllers.AuthController;
import com.example.taskmanagement.dtos.requests.LoginRequest;
import com.example.taskmanagement.dtos.requests.SignUpRequest;
import com.example.taskmanagement.dtos.responses.ApiResponse;
import com.example.taskmanagement.dtos.responses.LoginResponse;
import com.example.taskmanagement.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerImplementation implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<LoginResponse>> signUp(@RequestBody @Valid SignUpRequest request, HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<LoginResponse>builder()
                .data(authService.signUp(request))
                .isSuccessful(true)
                .status(HttpStatus.OK.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build());

    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request, HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ApiResponse.<LoginResponse>builder()
                .data(authService.login(request))
                .isSuccessful(true)
                .status(HttpStatus.OK.value())
                .path(httpServletRequest.getRequestURI())
                .timeStamp(Instant.now())
                .build());

    }

}
