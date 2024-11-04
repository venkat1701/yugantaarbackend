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

/**
 * Represents a user's profile in the application.
 * <p>
 * The UserProfile class provides detailed information about a user,
 * including personal details and contact information. It is linked to
 * the User entity to maintain a one-to-one relationship.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserProfile {

    /**
     * Unique identifier for the user profile.
     * <p>
     * This field is automatically generated and serves as the primary key for the UserProfile entity.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user associated with this profile.
     * <p>
     * This establishes a one-to-one relationship with the User entity,
     * where each user can have a corresponding profile.
     * </p>
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The user's first name.
     * <p>
     * This field is required and must not be blank.
     * </p>
     */
    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    /**
     * The user's last name.
     * <p>
     * This field is required and must not be blank.
     * </p>
     */
    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    /**
     * The user's phone number.
     * <p>
     * This field must match the specified regex pattern for international phone numbers.
     * </p>
     */
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$")
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * The user's address.
     * <p>
     * This field can store a large amount of text, allowing for detailed address descriptions.
     * </p>
     */
    @Lob
    private String address;

    /**
     * The user's date of birth.
     * <p>
     * This field must be a past date, ensuring that the user is of legal age.
     * </p>
     */
    @Past
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    /**
     * URI for the user's profile picture.
     * <p>
     * This field stores the location of the profile picture associated with the user.
     * </p>
     */
    @Column(name = "profile_picture")
    private String profilePictureURI;

    /**
     * A short biography about the user.
     * <p>
     * This field can store a large amount of text to provide more context about the user.
     * </p>
     */
    @Lob
    private String bio;

    /**
     * Timestamp indicating when the user profile was created.
     * <p>
     * This field is automatically populated with the creation time of the profile record.
     * </p>
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private String createdAt;

    /**
     * Timestamp indicating when the user profile was last updated.
     * <p>
     * This field is automatically populated with the last updated time of the profile record.
     * </p>
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
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
