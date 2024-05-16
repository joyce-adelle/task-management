package com.example.taskmanagement.config;

import com.example.taskmanagement.entities.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class PrincipalUser implements UserDetails {

    private final User user;
    private final String email;
    private final String password;

    private Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    public static PrincipalUser create(User user) {
        Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
        return new PrincipalUser(user, user.getEmail(), user.getPassword(), authorities);
    }

    public static Set<SimpleGrantedAuthority> getAuthorities(User user) {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    @Serial
    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

}
