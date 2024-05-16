package com.example.taskmanagement.repositories;

import com.example.taskmanagement.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findByIdAndCreatedById(Integer id, String userId);

    void deleteByIdAndCreatedById(Integer id, String userId);

    Page<Task> findAllByCreatedById(String userId, Pageable page);

}