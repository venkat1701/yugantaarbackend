package io.github.venkat1701.yugantaarbackend.controllers.implementation.events;

import io.github.venkat1701.yugantaarbackend.controllers.core.events.EventsController;
import io.github.venkat1701.yugantaarbackend.dto.events.EventDTO;
import io.github.venkat1701.yugantaarbackend.models.events.Event;
import io.github.venkat1701.yugantaarbackend.services.core.events.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implementation class for handling HTTP requests related to event management.
 * This controller provides RESTful endpoints to manage events, including creating,
 * reading, updating, deleting, and searching events.
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@RestController
@RequestMapping("/api/v1/events")
public class EventsControllerImplementation implements EventsController<Event, EventDTO, Long> {

    private final EventService eventService;

    /**
     * Constructs an instance of the EventsControllerImplementation class.
     *
     * @param eventService the service layer used to perform operations related to events
     */
    public EventsControllerImplementation(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Creates a new event from the provided {@link EventDTO}.
     *
     * @param entity the data transfer object containing event details
     * @return a {@link ResponseEntity} containing the created event and HTTP status code 201 (Created)
     */
    @Override
    @PostMapping("/create")
    public ResponseEntity<Event> create(@RequestBody EventDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.eventService.registerEvent(entity));
    }

    /**
     * Retrieves all events available in the system.
     *
     * @return a {@link ResponseEntity} containing a list of events and HTTP status code 200 (OK)
     */
    @Override
    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.eventService.getAll());
    }

    /**
     * Retrieves an event by its unique ID.
     *
     * @param aLong the unique identifier of the event to be retrieved
     * @return a {@link ResponseEntity} containing the event if found, or HTTP status code 404 (Not Found) if not found
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long aLong) {
        return this.eventService.findById(aLong)
                .map(event -> ResponseEntity.status(HttpStatus.OK).body(event))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing event identified by its ID.
     *
     * @param id the unique identifier of the event to be updated
     * @param entity the event object containing the updated details
     * @return a {@link ResponseEntity} containing the updated event and HTTP status code 200 (OK),
     *         or HTTP status code 404 (Not Found) if the event is not found
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id,@RequestBody Event entity) {
        if (this.eventService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(this.eventService.update(id, entity).get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes an event identified by its unique ID.
     *
     * @param aLong the unique identifier of the event to be deleted
     * @return a {@link ResponseEntity} with HTTP status code 204 (No Content) if the deletion is successful,
     *         or HTTP status code 404 (Not Found) if the event is not found
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long aLong) {
        if (this.eventService.delete(aLong)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Searches for events using pagination and sorting.
     *
     * @param page the page number to retrieve
     * @param size the number of events per page
     * @param sort the sorting criteria in the format "property,direction" (e.g., "name,asc")
     * @return a {@link ResponseEntity} containing a paginated list of events and HTTP status code 200 (OK)
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<Event>> search(@RequestParam int page,@RequestParam int size,@RequestParam String sort) {
        PageRequest pageRequest;
        if (sort != null && !sort.isEmpty()) {
            String[] sortParameters = sort.split(",");
            Sort.Direction direction = sortParameters.length > 1 ?
                    Sort.Direction.fromString(sortParameters[1]) : Sort.Direction.ASC;

            pageRequest = PageRequest.of(page, size, Sort.by(direction, sortParameters[0]));
        } else {
            pageRequest = PageRequest.of(page, size);
        }
        Page<Event> events = this.eventService.search(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    /**
     * Retrieves an event by its name.
     *
     * @param name the name of the event to be retrieved
     * @return a {@link ResponseEntity} containing the event if found, or HTTP status code 404 (Not Found) if not found
     */
    @Override
    @GetMapping("/{name}")
    public ResponseEntity<Event> getByName(@PathVariable String name) {
        return this.eventService.findByName(name)
                .map(event -> ResponseEntity.status(HttpStatus.OK).body(event))
                .orElse(ResponseEntity.notFound().build());
    }
}
