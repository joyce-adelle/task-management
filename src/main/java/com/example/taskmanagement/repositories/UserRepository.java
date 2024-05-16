package com.example.taskmanagement.repositories;

import com.example.taskmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}