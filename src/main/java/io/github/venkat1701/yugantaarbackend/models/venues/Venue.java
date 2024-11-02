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

@Entity
@Table(name="venues")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;


    private String address;
    private int capacity;
    private String description;

    @NotBlank
    @Column(name="contact_person")
    private String contactPerson;

    @Email
    @Column(name="contact_email")
    private String contactEmail;

    @Pattern(regexp="^\\+?[1-9]\\d{1,14}$")
    private String contactPhone;

    @OneToMany(mappedBy="venue")
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
