package com.example.taskmanagement.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
    private String message;

}
