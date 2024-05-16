package com.example.taskmanagement.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserRequest {

	@NotBlank(message="Name is required")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
	private String name;

}
