package com.example.taskmanagement.services;

import com.example.taskmanagement.dtos.requests.TaskRequest;
import com.example.taskmanagement.dtos.responses.MessageResponse;
import com.example.taskmanagement.dtos.responses.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest);

    TaskResponse updateTask(Integer id, TaskRequest taskRequest);

    TaskResponse getTask(Integer id);

    List<TaskResponse> getAllTasks();

    List<TaskResponse> getUserTasks();

    MessageResponse deleteTask(Integer id);

}
