package com.example.taskmanagement.dtos.responses;

import com.example.taskmanagement.utils.Role;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserResponse {

    private Integer id;

    private String name;

    private String email;

    private Role role;

    private Instant createdAt;

    private Instant updatedAt;

}
