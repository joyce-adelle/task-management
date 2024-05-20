package com.example.taskmanagement.websocket;

import com.example.taskmanagement.dtos.responses.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Data
public class WebSocketResponse {

	private Instant timeStamp;
	private TaskResponse data;
	private EventType event;

}
