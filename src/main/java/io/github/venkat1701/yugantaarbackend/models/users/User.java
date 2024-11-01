package io.github.venkat1701.yugantaarbackend.models.users;

import io.github.venkat1701.yugantaarbackend.models.roles.RolesEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String username;

    @Email
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private RolesEnum role;

    @CreationTimestamp
    private String createdAt;

    @UpdateTimestamp
    private String updatedAt;

    @CurrentTimestamp
    private String isActive;
}
