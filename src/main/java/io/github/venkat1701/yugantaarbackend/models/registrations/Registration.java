package io.github.venkat1701.yugantaarbackend.models.registrations;

import io.github.venkat1701.yugantaarbackend.models.events.Event;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a registration for an event by a user.
 * <p>
 * This entity captures the relationship between users and events through registrations.
 * It includes details such as the registration status, dates of registration, and
 * any cancellation reasons. The Registration entity serves as a link between the User
 * and Event entities, allowing for the management of user participation in events.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "registrations")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @NotBlank
    @Column(name = "registration_status")
    private String registrationStatus;

    @Column(name = "registration_date")
    private String registrationDate;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Compares this Registration object to another object for equality.
     *
     * @param o the object to be compared
     * @return true if this Registration is the same as the other object, false otherwise
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
        Registration that = (Registration) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    /**
     * Returns a hash code value for this Registration object.
     *
     * @return a hash code value for this Registration
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
