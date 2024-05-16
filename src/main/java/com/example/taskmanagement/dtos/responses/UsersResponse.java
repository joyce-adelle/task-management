package com.example.taskmanagement.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsersResponse {

    private List<UserResponse> users;

    private long total;

}
