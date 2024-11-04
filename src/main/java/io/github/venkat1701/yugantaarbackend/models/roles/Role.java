package io.github.venkat1701.yugantaarbackend.models.roles;

import io.github.venkat1701.yugantaarbackend.models.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a role that can be assigned to users within the application.
 * <p>
 * The Role class encapsulates the concept of roles in the system, which can define
 * permissions and access control for users. Each role can be associated with multiple
 * users and can have multiple permissions associated with it.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Embeddable
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "role_name")
    private String roleName;

    @Lob
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private String createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * A collection of RolePermission entities associated with this role.
     * <p>
     * This mapping allows each role to have multiple permissions defined,
     * enabling fine-grained access control. Each RolePermission instance represents
     * a specific permission that can be granted to users who are assigned this role.
     * </p>
     */
    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private Set<RolePermission> rolePermissions = new HashSet<>();

    /**
     * A collection of users associated with this role.
     * <p>
     * This many-to-many relationship allows multiple users to share the same role,
     * enabling consistent access control across the application. Users can have multiple
     * roles, thereby inheriting the permissions associated with those roles.
     * </p>
     */
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    /**
     * Compares this Role object to another object for equality.
     *
     * @param o the object to be compared
     * @return true if this Role is the same as the other object, false otherwise
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
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    /**
     * Returns a hash code value for this Role object.
     *
     * @return a hash code value for this Role
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
