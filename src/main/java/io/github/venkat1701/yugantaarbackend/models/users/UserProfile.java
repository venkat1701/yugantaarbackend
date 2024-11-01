package io.github.venkat1701.yugantaarbackend.models.users;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="userprofiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;


    private String phoneNumber;
    private String address;
    private Date dateOfBirth;
    private String profilePictureURI;

    @Lob
    private String bio;

    @CreationTimestamp
    private String createdAt;

    @UpdateTimestamp
    private String updatedAt;
}
