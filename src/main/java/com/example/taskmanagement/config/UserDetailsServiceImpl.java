package com.example.taskmanagement.config;

import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.exception.AppException;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.utils.Errors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public PrincipalUser loadUserByUsername(String username) {
        User user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new AppException(Errors.USER_NOT_FOUND));

        return PrincipalUser.create(user);
    }

}
