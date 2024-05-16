package com.example.taskmanagement.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TasksResponse {

    private List<TaskResponse> tasks;

    private long total;

}
