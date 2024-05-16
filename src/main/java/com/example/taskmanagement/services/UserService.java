package com.example.taskmanagement.services;

import com.example.taskmanagement.dtos.requests.UpdatePasswordRequest;
import com.example.taskmanagement.dtos.requests.UpdateUserRequest;
import com.example.taskmanagement.dtos.requests.UpdateUserRoleRequest;
import com.example.taskmanagement.dtos.responses.MessageResponse;
import com.example.taskmanagement.dtos.responses.UserResponse;
import com.example.taskmanagement.dtos.responses.UsersResponse;

import java.util.List;

public interface UserService {

    UserResponse getUser();

    UsersResponse getAllUsers(long offset, int limit);

    UserResponse updateUser(UpdateUserRequest request);

    MessageResponse updatePassword(UpdatePasswordRequest request);

    UserResponse updateUserRole(UpdateUserRoleRequest request);

}
