package com.example.taskmanagement.services;

import com.example.taskmanagement.dtos.requests.TaskRequest;
import com.example.taskmanagement.dtos.responses.MessageResponse;
import com.example.taskmanagement.dtos.responses.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest);

    TaskResponse updateTask(TaskRequest taskRequest);

    List<TaskResponse> getAllTasks();

    List<TaskResponse> getUserTasks();

    MessageResponse deleteTask();

}
