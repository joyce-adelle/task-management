package com.example.taskmanagement.entities;

import com.example.taskmanagement.utils.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters long")
    @NaturalId
    private String email;

    @JsonIgnore
    @NotNull(message = "password is required")
    private String password;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters long")
    private String name;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @NotNull
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "createdBy")
    private Set<Task> tasks;

    @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", email='" + email + '\'' +
               ", name='" + name + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               ", role=" + role +
               '}';
    }
}
