package io.github.venkat1701.yugantaarbackend.models.users;

import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the association between users and roles in the application.
 * <p>
 * The UserRole class serves as a bridge entity to establish a many-to-many relationship
 * between the User and Role entities. This allows a user to have multiple roles,
 * and each role can be assigned to multiple users.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "userroles")
@Embeddable
public class UserRole {

    /**
     * Unique identifier for the user-role association.
     * <p>
     * This field is automatically generated and serves as the primary key for the UserRole entity.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * The user associated with this role.
     * <p>
     * This establishes a many-to-one relationship with the User entity, allowing
     * a user to be linked to multiple roles through this association.
     * </p>
     */
    @ManyToOne // Assuming a User can have multiple roles
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The role associated with this user.
     * <p>
     * This establishes a many-to-one relationship with the Role entity, enabling
     * a role to be associated with multiple users through this user-role mapping.
     * </p>
     */
    @ManyToOne // Assuming a Role can belong to multiple users
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /**
     * Timestamp indicating when this user-role association was created.
     * <p>
     * This field is automatically populated with the creation time of the association record.
     * </p>
     */
    @CreationTimestamp
    private String createdAt;

}
