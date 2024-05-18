package com.example.taskmanagement.config.jwt;

import com.example.taskmanagement.config.jwt.PrincipalUser;
import com.example.taskmanagement.entities.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAware implements AuditorAware<User> {

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {

        Optional<Authentication> authentication = Optional
                .ofNullable(SecurityContextHolder.getContext().getAuthentication());

        if (authentication.isPresent()) {

            if (!(authentication.get() instanceof AnonymousAuthenticationToken)
                && authentication.get().isAuthenticated()) {
                return Optional.of(((PrincipalUser) authentication.get().getPrincipal()).getUser());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

}
