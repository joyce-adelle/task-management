package com.example.taskmanagement.repositories;

import com.example.taskmanagement.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}