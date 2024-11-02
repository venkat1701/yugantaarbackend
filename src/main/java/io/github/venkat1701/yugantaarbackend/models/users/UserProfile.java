package io.github.venkat1701.yugantaarbackend.models.users;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="user_profiles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @NotBlank
    @Column(name="first_name")
    private String firstName;


    @NotBlank
    @Column(name="last_name")
    private String lastName;

    @Pattern(regexp="^\\+?[1-9]\\d{1,14}$")
    @Column(name="phone_number")
    private String phoneNumber;

    @Lob
    private String address;

    @Past
    @Column(name="date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name="profile_picture")
    private String profilePictureURI;

    @Lob
    private String bio;

    @CreationTimestamp
    @Column(name="created_at")
    private String createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private String updatedAt;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserProfile that = (UserProfile) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
