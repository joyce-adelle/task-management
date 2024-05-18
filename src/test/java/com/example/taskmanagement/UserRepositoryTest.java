package com.example.taskmanagement;

import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.utils.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void givenUserCreated_whenFindByEmail_thenSuccess() {
        User newUser = User.builder()
                .email("testuser1@mail.com")
                .name("Test User")
                .password("password")
                .createdAt(Instant.now())
                .role(Role.USER)
                .build();

        entityManager.persist(newUser);

        Optional<User> user = userRepository.findByEmailIgnoreCase("testuser1@mail.com");
        assertThat(user).isNotEmpty();
        assertThat(user).contains(newUser);
        assertThat(user.get().getRole()).isEqualTo(Role.USER);

    }

    @Test
    @Transactional
    public void givenUserNotCreated_whenFindByEmail_thenFailure() {

        Optional<User> user = userRepository.findByEmailIgnoreCase("testuser1@mail.com");
        assertThat(user).isEmpty();

    }

    @Test
    @Transactional
    public void givenUserCreated_whenExitsByEmail_thenSuccess() {
        User newUser = User.builder()
                .email("testuser1@mail.com")
                .name("Test User")
                .password("password")
                .createdAt(Instant.now())
                .role(Role.USER)
                .build();

        entityManager.persist(newUser);

        boolean exists = userRepository.existsByEmailIgnoreCase("testuser1@mail.com");
        assertTrue(exists);

    }

    @Test
    @Transactional
    public void givenUserNotCreated_whenExitsByEmail_thenFailure() {

        boolean exists = userRepository.existsByEmailIgnoreCase("testuser1@mail.com");
        assertFalse(exists);

    }

}
