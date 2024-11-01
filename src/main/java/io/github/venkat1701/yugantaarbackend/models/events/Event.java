package io.github.venkat1701.yugantaarbackend.models.events;

import jakarta.persistence.*;

@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String location;
    private String venueName;
    private int maxParticipants;
    private int currentParticipants;
    private String eventType;
    private int ticketPrice;
    private boolean isFeatured;
    private String status;
    private String createdAt;
    private String updatedAt;
}
