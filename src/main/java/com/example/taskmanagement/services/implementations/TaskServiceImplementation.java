package com.example.taskmanagement.services.implementations;

import com.example.taskmanagement.config.jwt.AuditAware;
import com.example.taskmanagement.dtos.requests.CreateTaskRequest;
import com.example.taskmanagement.dtos.requests.UpdateTaskRequest;
import com.example.taskmanagement.dtos.responses.*;
import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.exception.AppException;
import com.example.taskmanagement.repositories.TaskRepository;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final AuditAware auditAware;


    @Override
    public TaskResponse createTask(CreateTaskRequest taskRequest) {

        User currentUser = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        Task task = Task.builder()
                .name(taskRequest.getName())
                .deadline(taskRequest.getDeadline())
                .createdBy(currentUser)
                .build();

        if (!UtilClass.isNull(taskRequest.getDescription()))
            task.setDescription(taskRequest.getDescription());

        if (!UtilClass.isNull(taskRequest.getStatus()))
            task.setStatus(Status.valueOf(taskRequest.getStatus()));
        else
            task.setStatus(Status.TODO);

        if (!UtilClass.isNull(taskRequest.getStartedAt()))
            task.setStartedAt(taskRequest.getStartedAt());

        Task savedTask = taskRepository.save(task);

        return TaskResponse.builder()
                .id(savedTask.getId())
                .name(savedTask.getName())
                .createdAt(savedTask.getCreatedAt())
                .deadline(savedTask.getDeadline())
                .description(savedTask.getDescription())
                .startedAt(savedTask.getStartedAt())
                .status(savedTask.getStatus())
                .updatedAt(savedTask.getUpdatedAt())
                .build();

    }

    @Override
    public TaskResponse updateTask(Integer id, UpdateTaskRequest taskRequest) {

        User currentUser = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        Task task = taskRepository.findByIdAndCreatedById(id, currentUser.getId())
                .orElseThrow(() -> new AppException(Errors.TASK_NOT_FOUND));

        if (!UtilClass.isNull(taskRequest.getName()))
            task.setName(taskRequest.getName());

        if (!UtilClass.isNull(taskRequest.getDeadline()))
            task.setDeadline(taskRequest.getDeadline());

        if (!UtilClass.isNull(taskRequest.getDescription()))
            task.setDescription(taskRequest.getDescription());

        if (!UtilClass.isNull(taskRequest.getStatus()))
            task.setStatus(Status.valueOf(taskRequest.getStatus()));

        if (!UtilClass.isNull(taskRequest.getStartedAt()))
            task.setStartedAt(taskRequest.getStartedAt());

        Task savedTask = taskRepository.save(task);

        return TaskResponse.builder()
                .id(savedTask.getId())
                .name(savedTask.getName())
                .createdAt(savedTask.getCreatedAt())
                .deadline(savedTask.getDeadline())
                .description(savedTask.getDescription())
                .startedAt(savedTask.getStartedAt())
                .status(savedTask.getStatus())
                .updatedAt(savedTask.getUpdatedAt())
                .build();

    }

    @Override
    public TaskResponse getTask(Integer id) {

        User currentUser = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        Task task;
        if (currentUser.getRole().equals(Role.ADMIN))
            task = taskRepository.findById(id)
                    .orElseThrow(() -> new AppException(Errors.TASK_NOT_FOUND));
        else
            task = taskRepository.findByIdAndCreatedById(id, currentUser.getId())
                    .orElseThrow(() -> new AppException(Errors.TASK_NOT_FOUND));

        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .createdAt(task.getCreatedAt())
                .deadline(task.getDeadline())
                .description(task.getDescription())
                .startedAt(task.getStartedAt())
                .status(task.getStatus())
                .updatedAt(task.getUpdatedAt())
                .build();

    }

    @Override
    public AdminTaskResponse getTaskAdmin(Integer id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new AppException(Errors.TASK_NOT_FOUND));

        User user = task.getCreatedBy();

        return AdminTaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .createdAt(task.getCreatedAt())
                .deadline(task.getDeadline())
                .description(task.getDescription())
                .startedAt(task.getStartedAt())
                .status(task.getStatus())
                .updatedAt(task.getUpdatedAt())
                .createdBy(UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .createdAt(user.getCreatedAt())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .updatedAt(user.getUpdatedAt())
                        .build())
                .build();
    }

    @Override
    public AdminTasksResponse getAllTasks(long offset, int limit) {

        Pageable page = new OffsetBasedPageRequest(offset, limit, Sort.Direction.ASC, "name");
        List<AdminTaskResponse> taskRes = new ArrayList<>();
        Page<Task> tasks = taskRepository.findAll(page);

        for (Task task : tasks) {
            User user = task.getCreatedBy();
            taskRes.add(AdminTaskResponse.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .createdAt(task.getCreatedAt())
                    .deadline(task.getDeadline())
                    .description(task.getDescription())
                    .startedAt(task.getStartedAt())
                    .status(task.getStatus())
                    .updatedAt(task.getUpdatedAt())
                    .createdBy(UserResponse.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .createdAt(user.getCreatedAt())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .updatedAt(user.getUpdatedAt())
                            .build())
                    .build());
        }

        return AdminTasksResponse.builder()
                .tasks(taskRes)
                .total(tasks.getTotalElements())
                .build();

    }

    @Override
    public TasksResponse getUserTasks(long offset, int limit) {

        User currentUser = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        Pageable page = new OffsetBasedPageRequest(offset, limit, Sort.Direction.ASC, "name");
        List<TaskResponse> taskRes = new ArrayList<>();
        Page<Task> tasks = taskRepository.findAllByCreatedById(currentUser.getId(), page);

        for (Task task : tasks)
            taskRes.add(TaskResponse.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .createdAt(task.getCreatedAt())
                    .deadline(task.getDeadline())
                    .description(task.getDescription())
                    .startedAt(task.getStartedAt())
                    .status(task.getStatus())
                    .updatedAt(task.getUpdatedAt())
                    .build());

        return TasksResponse.builder()
                .tasks(taskRes)
                .total(tasks.getTotalElements())
                .build();

    }

    @Override
    public MessageResponse deleteTask(Integer id) {

        User currentUser = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        taskRepository.deleteByIdAndCreatedById(id, currentUser.getId());

        return MessageResponse.builder()
                .message("Task deleted successfully")
                .build();

    }

}
