package com.example.taskmanagement.services;

import com.example.taskmanagement.dtos.requests.UpdatePasswordRequest;
import com.example.taskmanagement.dtos.requests.UpdateUserRequest;
import com.example.taskmanagement.dtos.responses.MessageResponse;
import com.example.taskmanagement.dtos.responses.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getUser();

    List<UserResponse> getAllUsers();

    UserResponse updateUser(UpdateUserRequest request);

    MessageResponse updatePassword(UpdatePasswordRequest request);

    UserResponse updateUserRole();

}
