package com.example.taskmanagement.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminTasksResponse {

    private List<AdminTaskResponse> tasks;

    private long total;

}
