package com.example.taskmanagement.services;

import com.example.taskmanagement.dtos.requests.CreateTaskRequest;
import com.example.taskmanagement.dtos.requests.UpdateTaskRequest;
import com.example.taskmanagement.dtos.responses.*;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest taskRequest);

    TaskResponse updateTask(Integer id, UpdateTaskRequest taskRequest);

    TaskResponse getTask(Integer id);

    AdminTaskResponse getTaskAdmin(Integer id);

    AdminTasksResponse getAllTasks(long offset, int limit);

    TasksResponse getUserTasks(long offset, int limit);

    MessageResponse deleteTask(Integer id);

}
