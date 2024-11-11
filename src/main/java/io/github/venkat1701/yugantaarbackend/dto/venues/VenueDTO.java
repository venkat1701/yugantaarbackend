package io.github.venkat1701.yugantaarbackend.dto.venues;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VenueDTO {

    private String name;
    private String address;
    private int capacity;
    private String description;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;



}
