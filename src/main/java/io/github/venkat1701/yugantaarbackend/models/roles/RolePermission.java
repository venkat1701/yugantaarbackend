package io.github.venkat1701.yugantaarbackend.models.roles;

import io.github.venkat1701.yugantaarbackend.models.permissions.Permission;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents the association between a role and its permissions within the application.
 * <p>
 * The RolePermission class serves as a mapping entity that defines the permissions granted
 * to a specific role. This enables flexible access control, allowing roles to be associated
 * with various permissions that dictate what actions users assigned to those roles can perform.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "role_permissions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The role associated with this permission.
     * <p>
     * This many-to-one relationship allows multiple RolePermission instances to reference the same Role.
     * It enables roles to have multiple permissions defined through this association.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * The permission associated with this role.
     * <p>
     * This many-to-one relationship allows a single permission to be linked to multiple roles.
     * It is crucial for defining what actions users in this role are allowed to perform.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;

    /**
     * The timestamp indicating when this RolePermission was created.
     * <p>
     * This field is automatically populated by the system when a new RolePermission is created.
     * It helps track the lifecycle of the permissions associated with roles.
     * </p>
     */
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Compares this RolePermission object to another object for equality.
     *
     * @param o the object to be compared
     * @return true if this RolePermission is the same as the other object, false otherwise
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RolePermission that = (RolePermission) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    /**
     * Returns a hash code value for this RolePermission object.
     *
     * @return a hash code value for this RolePermission
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
