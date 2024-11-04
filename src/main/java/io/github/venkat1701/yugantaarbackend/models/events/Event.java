package io.github.venkat1701.yugantaarbackend.models.events;

import io.github.venkat1701.yugantaarbackend.models.registrations.Registration;
import io.github.venkat1701.yugantaarbackend.models.venues.Venue;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an event in the application.
 * <p>
 * This entity encapsulates all the necessary details related to an event,
 * including its name, description, date, location, and ticket pricing.
 * The Event entity is linked to registrations and sponsors, and it may be associated with a venue.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @Lob
    private String description;

    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date")
    private String endDate;

    @NotBlank
    private String location;

    @NotBlank
    @Column(name = "venue_name")
    private String venueName;

    @Min(1)
    @Column(name = "max_participants")
    private int maxParticipants;

    @Column(name = "current_participants")
    private int currentParticipants;

    @NotBlank
    @Column(name = "event_type")
    private String eventType;

    @Positive
    @Column(name = "ticket_price")
    private int ticketPrice;

    @Column(name = "is_featured")
    private boolean isFeatured;

    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    private Set<Registration> registrations = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    private Set<EventSponsor> eventSponsors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    /**
     * Compares this Event object to another object for equality.
     *
     * @param o the object to be compared
     * @return true if this Event is the same as the other object, false otherwise
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
        Event event = (Event) o;
        return getId() != null && Objects.equals(getId(), event.getId());
    }

    /**
     * Returns a hash code value for this Event object.
     *
     * @return a hash code value for this Event
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
