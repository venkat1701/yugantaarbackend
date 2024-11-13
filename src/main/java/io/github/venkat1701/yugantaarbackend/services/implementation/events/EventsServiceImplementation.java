package io.github.venkat1701.yugantaarbackend.services.implementation.events;

import io.github.venkat1701.yugantaarbackend.dto.events.EventDTO;
import io.github.venkat1701.yugantaarbackend.mappers.implementation.EventMapper;
import io.github.venkat1701.yugantaarbackend.models.events.Event;
import io.github.venkat1701.yugantaarbackend.models.venues.Venue;
import io.github.venkat1701.yugantaarbackend.repositories.events.EventsRepository;
import io.github.venkat1701.yugantaarbackend.repositories.venue.VenueRepository;
import io.github.venkat1701.yugantaarbackend.services.core.commons.GenericCrudService;
import io.github.venkat1701.yugantaarbackend.services.core.events.EventService;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing events.
 * <p>
 * This class handles CRUD operations for events, including searching,
 * retrieving, updating, and deleting event data, as well as creating new events.
 * The service ensures proper permission checks using custom annotations.
 * </p>
 *
 * <p><strong>Author:</strong> Venkat</p>
 */
@Service
public class EventsServiceImplementation implements EventService{

    private final EventsRepository eventsRepository;
    private final VenueRepository venueRepository;
    private final EventMapper eventMapper;

    /**
     * Constructs an instance of EventsServiceImplementation.
     *
     * @param eventsRepository the repository for accessing and managing event data
     * @param venueRepository the repository for accessing venue data
     */
    public EventsServiceImplementation(EventsRepository eventsRepository, VenueRepository venueRepository, EventMapper mapper) {
        this.eventsRepository = eventsRepository;
        this.venueRepository = venueRepository;
        this.eventMapper = mapper;
    }

    /**
     * Searches for events with pagination support.
     *
     * @param pageRequest the pagination information
     * @return a paginated list of events
     */
    @Override
    @RequiresEventReadPermission
    public Page<EventDTO> search(PageRequest pageRequest) {
        Page<Event> eventPage = this.eventsRepository.findAll(pageRequest);
        return eventPage.map(this.eventMapper::toDTO);
    }


    /**
     * Retrieves a list of all events.
     *
     * @return a list of all event entities
     */
    @Override
    @RequiresUserReadPermission
    public List<EventDTO> getAll() {
        return this.eventsRepository.findAll().stream().map(this.eventMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Finds an event by its ID.
     *
     * @param id the ID of the event to find
     * @return an Optional containing the event if found, or empty if not
     */
    @Override
    @RequiresEventReadPermission
    public Optional<EventDTO> findById(Long id) {
        return Optional.of(this.eventMapper.toDTO(this.eventsRepository.findById(id).get()));
    }

    /**
     * Updates an existing event by its ID.
     *
     * @param id the ID of the event to update
     * @param event the event details to update
     * @return an Optional containing the updated event if the ID exists, or empty if not
     */
    @Override
    @Transactional
    @RequiresEventUpdatePermission
    public Optional<EventDTO> update(Long id, EventDTO event) {
        if (this.eventsRepository.existsById(id)) {
            return Optional.of(this.eventMapper.toDTO(this.eventsRepository.save(this.eventMapper.toEntity(event))));
        }
        return Optional.empty();
    }

    /**
     * Deletes an event by its ID.
     *
     * @param id the ID of the event to delete
     * @return true if the event was successfully deleted, false otherwise
     */
    @Override
    @Transactional
    @RequiresEventDeletePermission
    public boolean delete(Long id) {
        if (this.eventsRepository.existsById(id)) {
            this.eventsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<EventDTO> findByName(String name) {
        if(this.eventsRepository.existsByName(name)) {
            return Optional.of(this.eventMapper.toDTO(this.eventsRepository.findByName(name).get()));
        }
        return Optional.empty();
    }

    /**
     * Registers a new event using the provided EventDTO.
     *
     * @param eventDTO the data transfer object containing event details
     * @return the saved Event entity, or null if the venue is not found
     */
    @Transactional
    @RequiresEventCreatePermission
    public EventDTO registerEvent(EventDTO eventDTO) {
        // Validate and retrieve the venue from the repository
        Optional<Venue> venueOptional = this.venueRepository.findByName(eventDTO.getVenueName());
        if (venueOptional.isEmpty()) {
            throw new IllegalArgumentException("Venue not found: " + eventDTO.getVenueName());
        }

        Event event = this.getEvent(eventDTO, venueOptional);

        // Save and return the newly created event
        return this.eventMapper.toDTO(this.eventsRepository.save(event));
    }

    @NotNull
    private Event getEvent(EventDTO eventDTO, Optional<Venue> venueOpt) {
        Venue venue = venueOpt.get();

        // Construct a new Event entity from the provided DTO
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setLocation(eventDTO.getLocation());
        event.setVenueName(venue.getName());
        event.setVenue(venue);
        event.setTicketPrice(eventDTO.getTicketPrice());
        event.setFeatured(eventDTO.isFeatured());
        return event;
    }
}
