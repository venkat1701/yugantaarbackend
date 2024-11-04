package io.github.venkat1701.yugantaarbackend.models.venues;

import io.github.venkat1701.yugantaarbackend.models.events.Event;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a venue where events can be held.
 * <p>
 * The Venue class stores information about the physical location of events, including
 * details like name, address, capacity, and contact information.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "venues")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Venue {

    /**
     * Unique identifier for the venue.
     * <p>
     * This field is automatically generated and serves as the primary key for the Venue entity.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the venue.
     * <p>
     * This field is mandatory and cannot be left blank.
     * </p>
     */
    @NotBlank
    private String name;

    /**
     * The physical address of the venue.
     * <p>
     * This field is optional and may contain additional information about the venue's location.
     * </p>
     */
    private String address;

    /**
     * The maximum capacity of the venue.
     * <p>
     * This field indicates how many attendees the venue can accommodate.
     * </p>
     */
    private int capacity;

    /**
     * A brief description of the venue.
     * <p>
     * This field is optional and can provide additional context about the venue.
     * </p>
     */
    private String description;

    /**
     * The name of the contact person for the venue.
     * <p>
     * This field is mandatory and cannot be left blank.
     * </p>
     */
    @NotBlank
    @Column(name = "contact_person")
    private String contactPerson;

    /**
     * The email address of the contact person.
     * <p>
     * This field must contain a valid email format and is used for communication regarding events at the venue.
     * </p>
     */
    @Email
    @Column(name = "contact_email")
    private String contactEmail;

    /**
     * The phone number of the contact person.
     * <p>
     * This field must match a specific pattern for valid international phone numbers.
     * </p>
     */
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$")
    private String contactPhone;

    /**
     * A set of events associated with this venue.
     * <p>
     * This establishes a one-to-many relationship, where each venue can host multiple events.
     * </p>
     */
    @OneToMany(mappedBy = "venue")
    @ToString.Exclude
    private Set<Event> events = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Venue venue = (Venue) o;
        return getId() != null && Objects.equals(getId(), venue.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
