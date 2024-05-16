package com.example.taskmanagement.dtos.responses;

import com.example.taskmanagement.utils.Status;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TaskResponse {

    private Integer id;

    private String name;

    private String description;

    private Status status;

    private Instant startedAt;

    private Instant deadline;

    private Instant createdAt;

    private Instant updatedAt;

}
