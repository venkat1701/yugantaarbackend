package io.github.venkat1701.yugantaarbackend.models.events;


import jakarta.persistence.*;

@Entity
@Table(name="sponsors")
public class EventSponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int eventId;

    private int sponsorId;

    private int sponsorshipAmount;

    private String createdAt;

}
