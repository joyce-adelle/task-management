package com.example.taskmanagement.dtos.requests;

import com.example.taskmanagement.annotations.validations.enums.ValueOfEnum;
import com.example.taskmanagement.utils.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Data
public class UpdateTaskRequest {

    @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters long")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", fallbackPatterns = { "yyyy-MM-dd'T'hh:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd hh:mm:ss" })
    private Instant deadline;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", fallbackPatterns = { "yyyy-MM-dd'T'hh:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd hh:mm:ss" })
    private Instant startedAt;

    @ValueOfEnum(enumClass = Status.class, checkWithUpperCase = true, message = "Status must be any of done, todo, or inprogress")
    private String status;

    private String description;

}
