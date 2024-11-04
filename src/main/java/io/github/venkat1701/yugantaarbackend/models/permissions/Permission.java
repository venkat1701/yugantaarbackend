package io.github.venkat1701.yugantaarbackend.models.permissions;

import io.github.venkat1701.yugantaarbackend.models.roles.RolePermission;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a permission within the application.
 * <p>
 * This entity defines a permission that can be granted to roles. Each permission has a unique identifier,
 * a name, and an optional description. Additionally, the entity tracks the creation and update timestamps.
 * The relationship with role permissions is captured through the {@link RolePermission} entity.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "permissions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "permission_name")
    private String permissionName;

    @Lob
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private String updatedAt;

    @OneToMany(mappedBy = "permission")
    @ToString.Exclude
    private Set<RolePermission> rolePermissions = new HashSet<>();

    /**
     * Compares this Permission object to another object for equality.
     *
     * @param o the object to be compared
     * @return true if this Permission is the same as the other object, false otherwise
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
        Permission that = (Permission) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    /**
     * Returns a hash code value for this Permission object.
     *
     * @return a hash code value for this Permission
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
