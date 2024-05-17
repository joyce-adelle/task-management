package com.example.taskmanagement.services.implementations;

import com.example.taskmanagement.config.AuditAware;
import com.example.taskmanagement.dtos.requests.UpdatePasswordRequest;
import com.example.taskmanagement.dtos.requests.UpdateUserRequest;
import com.example.taskmanagement.dtos.requests.UpdateUserRoleRequest;
import com.example.taskmanagement.dtos.responses.MessageResponse;
import com.example.taskmanagement.dtos.responses.UserResponse;
import com.example.taskmanagement.dtos.responses.UsersResponse;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.exception.AppException;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.services.UserService;
import com.example.taskmanagement.utils.Errors;
import com.example.taskmanagement.utils.OffsetBasedPageRequest;
import com.example.taskmanagement.utils.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final AuditAware auditAware;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse getUser() {

        User currentUser = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        return UserResponse.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .createdAt(currentUser.getCreatedAt())
                .email(currentUser.getEmail())
                .role(currentUser.getRole())
                .updatedAt(currentUser.getUpdatedAt())
                .build();

    }

    @Override
    public UsersResponse getAllUsers(long offset, int limit) {

        Pageable page = new OffsetBasedPageRequest(offset, limit, Sort.Direction.ASC, "name");
        List<UserResponse> usersRes = new ArrayList<>();
        Page<User> users = userRepository.findAll(page);

        for (User user : users)
            usersRes.add(UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .createdAt(user.getCreatedAt())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .updatedAt(user.getUpdatedAt())
                    .build());

        return UsersResponse.builder()
                .users(usersRes)
                .total(users.getTotalElements())
                .build();

    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request) {

        User currentUser = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        currentUser.setName(request.getName());

        User savedUser = userRepository.save(currentUser);

        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .createdAt(savedUser.getCreatedAt())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .updatedAt(savedUser.getUpdatedAt())
                .build();

    }

    @Override
    public MessageResponse updatePassword(UpdatePasswordRequest request) {

        User user = auditAware.getCurrentAuditor()
                .orElseThrow(() -> new AppException(Errors.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            throw new AppException(Errors.INVALID_PASSWORD);

        String newPassword = request.getNewPassword();

        if (newPassword.equals(request.getOldPassword())) {
            throw new AppException(Errors.DUPLICATE_PASSWORDS);
        }

        try {

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            return MessageResponse.builder().message("New password set successfully").build();

        } catch (Exception e) {
            throw new AppException(Errors.ERROR_OCCURRED_PERFORMING_ACTION);
        }
    }

    @Override
    public UserResponse updateUserRole(UpdateUserRoleRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

        user.setRole(Role.valueOf(request.getRole()));
        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .createdAt(savedUser.getCreatedAt())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .updatedAt(savedUser.getUpdatedAt())
                .build();

    }

}
