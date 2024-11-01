package io.github.venkat1701.yugantaarbackend.models.registrations;

import jakarta.persistence.*;

@Entity
@Table(name="registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int userId;
    private int eventId;

    private String registrationStatus;
    private String registrationDate;

    private String cancellationReason;
    private String createdAt;
    private String updatedAt;
}
