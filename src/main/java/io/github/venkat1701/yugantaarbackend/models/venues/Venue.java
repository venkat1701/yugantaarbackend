package io.github.venkat1701.yugantaarbackend.models.venues;


import jakarta.persistence.*;

@Entity
@Table(name="venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String address;
    private int capacity;
    private String description;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String createdAt;
    private String updatedAt;
}
