package com.example.taskmanagement.repositories;

import com.example.taskmanagement.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findByIdAndCreatedBy(Integer id, String userId);

    void deleteByIdAndCreatedBy(Integer id, String userId);

    Page<Task> findAllByCreatedBy(String userId, Pageable page);

}