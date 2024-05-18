package com.example.taskmanagement;

import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.repositories.TaskRepository;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.utils.OffsetBasedPageRequest;
import com.example.taskmanagement.utils.Role;
import com.example.taskmanagement.utils.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private final User newUser = User.builder()
            .email("testuser1@mail.com")
            .name("Test User")
            .password("password")
            .createdAt(Instant.now())
            .role(Role.USER)
            .build();

    @Test
    @Transactional
    public void givenTaskCreated_whenFindByIdAndCreatedById_thenSuccess() {

        User user = userRepository.save(newUser);

        Task newTask = Task.builder()
                .deadline(Instant.now().plus(5, ChronoUnit.DAYS))
                .name("Test Task")
                .createdBy(user)
                .status(Status.TODO)
                .build();


        Task savedTask = taskRepository.save(newTask);

        Optional<Task> task = taskRepository.findByIdAndCreatedById(savedTask.getId(), user.getId());
        assertThat(task).isNotEmpty();
        assertThat(task).contains(savedTask);
        assertThat(task.get().getCreatedBy()).isEqualTo(user);
        assertThat(task.get().getStatus()).isEqualTo(Status.TODO);

    }

    @Test
    @Transactional
    public void givenTaskNotCreated_whenFindByIdAndCreatedById_thenFailure() {

        User user = userRepository.save(newUser);
        Optional<Task> task = taskRepository.findByIdAndCreatedById(0, user.getId());
        assertThat(task).isEmpty();

    }

    @Test
    @Transactional
    public void givenTaskCreated_whenFindAllByIdAndCreatedById_thenSuccess() {

        User user = userRepository.save(newUser);

        Task newTask = Task.builder()
                .deadline(Instant.now().plus(5, ChronoUnit.DAYS))
                .name("Test Task")
                .createdBy(user)
                .status(Status.TODO)
                .build();


        Task savedTask = taskRepository.save(newTask);

        Page<Task> tasks = taskRepository.findAllByCreatedById(user.getId(), new OffsetBasedPageRequest(0, 3));

        assertThat(tasks.getNumberOfElements()).isEqualTo(1);
        assertThat(tasks.getContent()).extracting(Task::getId).containsOnly(savedTask.getId());

    }

    @Test
    @Transactional
    public void givenTaskCreated_whenDeletedByIdAndCreatedById_thenSuccess() {

        User user = userRepository.save(newUser);

        Task newTask = Task.builder()
                .deadline(Instant.now().plus(5, ChronoUnit.DAYS))
                .name("Test Task")
                .createdBy(user)
                .status(Status.TODO)
                .build();


        Task savedTask = taskRepository.save(newTask);

        Optional<Task> task = taskRepository.findByIdAndCreatedById(savedTask.getId(), user.getId());
        assertThat(task).isNotEmpty();
        assertThat(task).contains(savedTask);

        taskRepository.deleteByIdAndCreatedById(savedTask.getId(), user.getId());
        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks.size()).isEqualTo(5);

    }

    @Test
    @Transactional
    public void givenTasksCreatedByDifferentUsers_whenDeletedByIdAndCreatedById_thenSuccess() {

        User user = userRepository.save(newUser);

        Task newTask = Task.builder()
                .deadline(Instant.now().plus(5, ChronoUnit.DAYS))
                .name("Test Task")
                .createdBy(user)
                .status(Status.TODO)
                .build();

        Task savedTask = taskRepository.save(newTask);

        Optional<Task> task = taskRepository.findByIdAndCreatedById(savedTask.getId(), user.getId());
        assertThat(task).isNotEmpty();
        assertThat(task).contains(savedTask);

        taskRepository.deleteByIdAndCreatedById(savedTask.getId(), user.getId());

        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks.size()).isEqualTo(5);
        assertThat(tasks).extracting(Task::getId).doesNotContain(savedTask.getId());

    }

}
