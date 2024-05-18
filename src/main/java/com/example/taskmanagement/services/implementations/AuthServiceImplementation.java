package com.example.taskmanagement.services.implementations;

import com.example.taskmanagement.config.jwt.JwtService;
import com.example.taskmanagement.dtos.requests.LoginRequest;
import com.example.taskmanagement.dtos.requests.SignUpRequest;
import com.example.taskmanagement.dtos.responses.LoginResponse;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.exception.AppException;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.services.AuthService;
import com.example.taskmanagement.utils.Errors;
import com.example.taskmanagement.utils.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public LoginResponse signUp(SignUpRequest signUpRequest) {

        try {

            boolean checkEmail = userRepository.existsByEmailIgnoreCase(signUpRequest.getEmail());
            if (checkEmail)
                throw new AppException(Errors.DUPLICATE_USER_EMAIL);

            User user = User.builder()
                    .email(signUpRequest.getEmail().toLowerCase())
                    .name(signUpRequest.getName())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .role(Role.USER)
                    .build();

            userRepository.save(user);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("New sign up for user with email " + user.getEmail());

            return LoginResponse.builder()
                    .token(jwtService.generateToken(authentication)).build();

        } catch (AppException e) {
            log.error(e.getMessage(), e.getCause());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new AppException(Errors.ERROR_OCCURRED_PERFORMING_ACTION);
        }

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        try {

            User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                    .orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                throw new AppException(Errors.INVALID_LOGIN_DETAILS);

            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("New login by user with email " + user.getEmail());

            return LoginResponse.builder()
                    .token(jwtService.generateToken(authentication))
                    .build();

        } catch (AppException e) {
            log.error(e.getMessage(), e.getCause());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new AppException(Errors.ERROR_OCCURRED_PERFORMING_ACTION);
        }

    }


}
