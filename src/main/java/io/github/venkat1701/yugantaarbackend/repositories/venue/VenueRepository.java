package io.github.venkat1701.yugantaarbackend.repositories.venue;

import io.github.venkat1701.yugantaarbackend.models.venues.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    Optional<Venue> findByName(String name);
}
