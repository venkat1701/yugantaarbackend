package io.github.venkat1701.yugantaarbackend.services.core.venues;

import io.github.venkat1701.yugantaarbackend.dto.venues.VenueDTO;
import io.github.venkat1701.yugantaarbackend.models.venues.Venue;
import io.github.venkat1701.yugantaarbackend.services.core.commons.GenericCrudService;

import java.util.Optional;

public interface VenueService extends GenericCrudService<Venue, Long> {

    Venue registerVenue(VenueDTO venueDTO);


    Optional<Venue> findByName(String name);
}
