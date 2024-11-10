package io.github.venkat1701.yugantaarbackend.repositories.events;

import io.github.venkat1701.yugantaarbackend.models.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByName(String name);

    boolean existsByName(String name);
}
