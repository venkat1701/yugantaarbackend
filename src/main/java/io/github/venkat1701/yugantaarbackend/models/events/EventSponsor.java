package io.github.venkat1701.yugantaarbackend.models.events;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a sponsorship for an event.
 * <p>
 * This entity captures the relationship between an event and its sponsors,
 * including the details of the sponsorship amount and the associated event.
 * The EventSponsor entity is linked to an Event, indicating which event
 * the sponsorship applies to.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "event_sponsor")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EventSponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "sponsor_id")
    private Long sponsorId;

    @Positive
    @Column(name = "sponsorship_amount")
    private int sponsorshipAmount;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Compares this EventSponsor object to another object for equality.
     *
     * @param o the object to be compared
     * @return true if this EventSponsor is the same as the other object, false otherwise
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
        EventSponsor that = (EventSponsor) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    /**
     * Returns a hash code value for this EventSponsor object.
     *
     * @return a hash code value for this EventSponsor
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
