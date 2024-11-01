package io.github.venkat1701.yugantaarbackend.models.users;


import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="userroles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private List<User> users = new ArrayList<>();

    private List<Role> roles = new ArrayList<>();

    @CreationTimestamp
    private String createdAt;
}
