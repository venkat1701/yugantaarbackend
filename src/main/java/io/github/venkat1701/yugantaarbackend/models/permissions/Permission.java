package io.github.venkat1701.yugantaarbackend.models.permissions;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String permissionName;
    private String description;

    @CreationTimestamp
    private String createdAt;
    @UpdateTimestamp
    private String updatedAt;
}
