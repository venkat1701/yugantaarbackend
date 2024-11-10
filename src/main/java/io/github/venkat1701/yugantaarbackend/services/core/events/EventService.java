package io.github.venkat1701.yugantaarbackend.services.core.events;

import io.github.venkat1701.yugantaarbackend.dto.events.EventDTO;
import io.github.venkat1701.yugantaarbackend.models.events.Event;
import io.github.venkat1701.yugantaarbackend.services.core.commons.GenericCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * Interface for defining service operations related to Event management.
 * This interface extends the {@link GenericCrudService} to inherit basic CRUD operations
 * and adds custom methods specific to event handling.
 *
 * <p>
 * Author: Venkat
 * </p>
 */
public interface EventService extends GenericCrudService<Event, Long> {

    /**
     * Searches for events with pagination support.
     *
     * @param pageRequest an object that specifies the pagination information, such as page number and size
     * @return a paginated list of events
     */
    Page<Event> search(PageRequest pageRequest);

    /**
     * Retrieves all events available in the database.
     *
     * @return a list of all events
     */
    List<Event> getAll();

    /**
     * Finds an event by its ID.
     *
     * @param id the unique identifier of the event to find
     * @return an {@link Optional} containing the found event, or empty if not found
     */
    Optional<Event> findById(Long id);

    /**
     * Updates an existing event by its ID.
     *
     * @param id the unique identifier of the event to update
     * @param event the event object containing updated information
     * @return an {@link Optional} containing the updated event if successful, or empty if not found
     */
    Optional<Event> update(Long id, Event event);

    /**
     * Deletes an event by its ID.
     *
     * @param id the unique identifier of the event to delete
     * @return true if the event was successfully deleted, false otherwise
     */
    boolean delete(Long id);

    /**
     * Finds an event by its name.
     *
     * @param name the name of the event to find
     * @return an {@link Optional} containing the found event, or empty if not found
     */
    Optional<Event> findByName(String name);

    /**
     * Registers a new event based on the provided {@link EventDTO} object.
     *
     * @param eventDTO the data transfer object containing the event details
     * @return the registered event entity
     */
    Event registerEvent(EventDTO eventDTO);
}
