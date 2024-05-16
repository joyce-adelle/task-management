package com.example.taskmanagement.services;

import com.example.taskmanagement.dtos.requests.LoginRequest;
import com.example.taskmanagement.dtos.requests.SignUpRequest;
import com.example.taskmanagement.dtos.responses.LoginResponse;

public interface AuthService {

    LoginResponse signUp(SignUpRequest signUpRequest);

    LoginResponse login(LoginRequest loginRequest);

}
