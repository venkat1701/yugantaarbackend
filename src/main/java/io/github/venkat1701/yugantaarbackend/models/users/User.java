package io.github.venkat1701.yugantaarbackend.models.users;

import io.github.venkat1701.yugantaarbackend.models.payments.Payment;
import io.github.venkat1701.yugantaarbackend.models.registrations.Registration;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a user in the application.
 * <p>
 * The User class serves as a blueprint for user entities, including authentication details,
 * roles, and relationships to user profiles, registrations, and payments. It captures essential
 * user information and enforces validation constraints on the fields.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

    /**
     * Unique identifier for the user.
     * <p>
     * This field is automatically generated and serves as the primary key for the User entity.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username for the user.
     * <p>
     * This field is required and must be unique across all users. It is used for user identification.
     * </p>
     */
    @NotBlank
    @Column(unique = true)
    private String username;

    /**
     * Email address of the user.
     * <p>
     * This field is required, must be in a valid email format, and must be unique across all users.
     * It is often used for authentication and communication purposes.
     * </p>
     */
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    /**
     * Password of the user.
     * <p>
     * This field is required and should be stored in a hashed format for security purposes.
     * </p>
     */
    @NotBlank
    private String password;

    /**
     * Roles associated with the user.
     * <p>
     * This field represents the set of roles assigned to the user, allowing for role-based access control.
     * It is mapped via a join table to support a many-to-many relationship.
     * </p>
     */
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ElementCollection
    private Set<Role> roles = new HashSet<>();

    /**
     * Timestamp indicating when the user was created.
     * <p>
     * This field is automatically populated with the creation time of the user record.
     * </p>
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the user was last updated.
     * <p>
     * This field is automatically populated with the last updated time of the user record.
     * </p>
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Indicates whether the user's account is active.
     * <p>
     * This field is used to enable or disable user accounts, allowing for account management.
     * </p>
     */
    @Column(name = "is_active")
    private Boolean isActive;

    /**
     * User profile associated with the user.
     * <p>
     * This represents a one-to-one relationship where each user may have an associated profile
     * containing additional information about the user.
     * </p>
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    /**
     * Registrations associated with the user.
     * <p>
     * This represents a one-to-many relationship where a user can register for multiple events.
     * </p>
     */
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Registration> registrations = new HashSet<>();

    /**
     * Payments associated with the user.
     * <p>
     * This represents a one-to-many relationship where a user can make multiple payments for events.
     * </p>
     */
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Payment> payments = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
