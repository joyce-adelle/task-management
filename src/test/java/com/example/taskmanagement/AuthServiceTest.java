package com.example.taskmanagement;

import com.example.taskmanagement.config.JwtService;
import com.example.taskmanagement.dtos.requests.LoginRequest;
import com.example.taskmanagement.dtos.requests.SignUpRequest;
import com.example.taskmanagement.dtos.responses.LoginResponse;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.exception.AppException;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.services.AuthService;
import com.example.taskmanagement.utils.Errors;
import com.example.taskmanagement.utils.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userNotCreated_whenEmailExits_thenSuccess() {

        User newUser = User.builder()
                .email("testuser@mail.com")
                .name("Test User")
                .password("password")
                .createdAt(Instant.now())
                .updatedAt(null)
                .role(Role.USER)
                .build();

        userRepository.save(newUser);

        SignUpRequest request = new SignUpRequest();
        request.setEmail("testuser@mail.com");
        request.setPassword("password");
        request.setName("Test User");

        Exception exception = assertThrows(AppException.class, () -> {
            authService.signUp(request);
        });

        assertThat(exception.getMessage()).isEqualTo(Errors.DUPLICATE_USER_EMAIL);

    }

    @Test
    public void userCreated_whenEmailNotExist_thenSuccess() {

        SignUpRequest request = new SignUpRequest();
        request.setEmail("testuser1@mail.com");
        request.setPassword("password");
        request.setName("Test User");

        LoginResponse res = authService.signUp(request);

        assertThat(res).isNotNull();
        String email = jwtService.extractEmail(res.getToken());
        assertThat(email).isEqualTo(request.getEmail());

    }

    @Test
    public void userNotLogin_whenPasswordInvalid_thenSuccess() {

        SignUpRequest sRequest = new SignUpRequest();
        sRequest.setEmail("testuser2@mail.com");
        sRequest.setPassword("password");
        sRequest.setName("Test User");

        authService.signUp(sRequest);

        LoginRequest request = new LoginRequest();
        request.setEmail("testuser2@mail.com");
        request.setPassword("invalidpassword");

        Exception exception = assertThrows(AppException.class, () -> {
            authService.login(request);
        });

        assertThat(exception.getMessage()).isEqualTo(Errors.INVALID_LOGIN_DETAILS);

    }

    @Test
    public void userLogin_whenPasswordValid_thenSuccess() {

        SignUpRequest sRequest = new SignUpRequest();
        sRequest.setEmail("testuser3@mail.com");
        sRequest.setPassword("password");
        sRequest.setName("Test User");

        authService.signUp(sRequest);

        LoginRequest request = new LoginRequest();
        request.setEmail("testuser3@mail.com");
        request.setPassword("password");

        LoginResponse res = authService.login(request);

        assertThat(res).isNotNull();
        String email = jwtService.extractEmail(res.getToken());
        assertThat(email).isEqualTo(request.getEmail());

    }

}
