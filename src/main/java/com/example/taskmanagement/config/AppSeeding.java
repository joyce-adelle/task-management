package com.example.taskmanagement.config;

import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.repositories.TaskRepository;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.utils.Role;
import com.example.taskmanagement.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppSeeding implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {

            User admin = userRepository.save(User.builder()
                    .email("adminuser@mail.com")
                    .password(passwordEncoder.encode("AdminPassword1!"))
                    .name("Default Admin")
                    .role(Role.ADMIN)
                    .build());

            User user = userRepository.save(User.builder()
                    .email("testuser@mail.com")
                    .password(passwordEncoder.encode("Password1!"))
                    .name("Default User")
                    .role(Role.USER)
                    .build());

            taskRepository.saveAll(List.of(
                    Task.builder()
                            .name("Learn Java")
                            .deadline(Instant.parse("2024-02-09T11:19:42.12Z"))
                            .startedAt(Instant.parse("2023-02-09T11:19:42.12Z"))
                            .status(Status.DONE)
                            .createdBy(admin)
                            .build(),
                    Task.builder()
                            .name("Learn JavaScript")
                            .deadline(Instant.parse("2024-03-12T11:19:42.12Z"))
                            .startedAt(Instant.parse("2024-01-09T11:19:42.12Z"))
                            .status(Status.INPROGRESS)
                            .createdBy(admin)
                            .build(),
                    Task.builder()
                            .name("Learn NestJS")
                            .description("Nest (NestJS) is a framework for building efficient, scalable Node.js server-side applications. It uses progressive JavaScript, is built with and fully supports TypeScript (yet still enables developers to code in pure JavaScript) and combines elements of OOP (Object Oriented Programming), FP (Functional Programming), and FRP (Functional Reactive Programming).")
                            .deadline(Instant.parse("2024-08-30T11:19:42.12Z"))
                            .status(Status.TODO)
                            .createdBy(admin)
                            .build()));

            taskRepository.saveAll(List.of(
                    Task.builder()
                            .name("Learn Data Structures")
                            .deadline(Instant.parse("2024-03-12T12:12:12.12Z"))
                            .description("A data structure is a data organization, and storage format that is usually chosen for efficient access to data.")
                            .createdBy(user)
                            .startedAt(Instant.parse("2022-03-12T12:12:12.12Z"))
                            .status(Status.INPROGRESS)
                            .build(),
                    Task.builder()
                            .name("Learn NestJS")
                            .description("Nest (NestJS) is a framework for building efficient, scalable Node.js server-side applications. It uses progressive JavaScript, is built with and fully supports TypeScript (yet still enables developers to code in pure JavaScript) and combines elements of OOP (Object Oriented Programming), FP (Functional Programming), and FRP (Functional Reactive Programming).")
                            .deadline(Instant.parse("2024-08-30T11:19:42.12Z"))
                            .status(Status.TODO)
                            .createdBy(user)
                            .build()));

        }

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

}
