package io.github.venkat1701.yugantaarbackend.controllers.implementation.events;

import io.github.venkat1701.yugantaarbackend.controllers.core.events.EventsController;
import io.github.venkat1701.yugantaarbackend.dto.events.EventDTO;
import io.github.venkat1701.yugantaarbackend.mappers.implementation.EventMapper;
import io.github.venkat1701.yugantaarbackend.models.events.Event;
import io.github.venkat1701.yugantaarbackend.services.core.events.EventService;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final EventMapper eventMapper;
    /**
     * Constructs an instance of the EventsControllerImplementation class.
     *
     * @param eventService the service layer used to perform operations related to events
     */
    public EventsControllerImplementation(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    /**
     * Creates a new event from the provided {@link EventDTO}.
     *
     * @param entity the data transfer object containing event details
     * @return a {@link ResponseEntity} containing the created event and HTTP status code 201 (Created)
     */
    @Override
    @PostMapping("/create")
    @RequiresEventCreatePermission
    public ResponseEntity<EventDTO> create(@RequestBody EventDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.eventService.registerEvent(entity));
    }

    /**
     * Retrieves all events available in the system.
     *
     * @return a {@link ResponseEntity} containing a list of events and HTTP status code 200 (OK)
     */
    @Override
    @GetMapping("/all")
    @RequiresEventReadPermission
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.eventService.getAll()
        );
    }

    /**
     * Retrieves an event by its unique ID.
     *
     * @param aLong the unique identifier of the event to be retrieved
     * @return a {@link ResponseEntity} containing the event if found, or HTTP status code 404 (Not Found) if not found
     */
    @Override
    @GetMapping("/{id}")
    @RequiresEventReadPermission
    public ResponseEntity<EventDTO> getById(@PathVariable Long aLong) {
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
    @RequiresEventUpdatePermission
    public ResponseEntity<EventDTO> update(@PathVariable Long id,@RequestBody EventDTO entity) {
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
    @RequiresEventDeletePermission
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
    @RequiresEventReadPermission
    public ResponseEntity<Page<EventDTO>> search(@RequestParam int page,@RequestParam int size,@RequestParam String sort) {
        PageRequest pageRequest;
        if (sort != null && !sort.isEmpty()) {
            String[] sortParameters = sort.split(",");
            Sort.Direction direction = sortParameters.length > 1 ?
                    Sort.Direction.fromString(sortParameters[1]) : Sort.Direction.ASC;

            pageRequest = PageRequest.of(page, size, Sort.by(direction, sortParameters[0]));
        } else {
            pageRequest = PageRequest.of(page, size);
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.eventService.search(pageRequest));
    }

    /**
     * Retrieves an event by its name.
     *
     * @param name the name of the event to be retrieved
     * @return a {@link ResponseEntity} containing the event if found, or HTTP status code 404 (Not Found) if not found
     */
    @Override
    @GetMapping("/{name}")
    @RequiresEventReadPermission
    public ResponseEntity<EventDTO> getByName(@PathVariable String name) {
        return this.eventService.findByName(name)
                .map(event -> ResponseEntity.status(HttpStatus.OK).body(event))
                .orElse(ResponseEntity.notFound().build());
    }
}
