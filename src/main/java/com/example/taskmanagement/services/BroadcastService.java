package com.example.taskmanagement.services;

import com.example.taskmanagement.dtos.responses.TaskResponse;
import com.example.taskmanagement.websocket.EventType;

public interface BroadcastService {

    public void broadcastTask(TaskResponse task, EventType type);

}
