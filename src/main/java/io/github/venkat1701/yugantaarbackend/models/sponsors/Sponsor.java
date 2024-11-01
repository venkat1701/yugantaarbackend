package io.github.venkat1701.yugantaarbackend.models.sponsors;

import jakarta.persistence.*;
import org.springframework.web.service.annotation.GetExchange;

@Entity
@Table(name="sponsors")
public class Sponsor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String name;
    private String logoUrl;
    private String websiteUrl;
    private String description;
    private String sponsorshipLevel;
    private String createdAt;
    private String updatedAt;

}
