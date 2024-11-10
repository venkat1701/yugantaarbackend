package io.github.venkat1701.yugantaarbackend.events;


import io.github.venkat1701.yugantaarbackend.controllers.implementation.events.EventsControllerImplementation;
import io.github.venkat1701.yugantaarbackend.dto.events.EventDTO;
import io.github.venkat1701.yugantaarbackend.models.events.Event;
import io.github.venkat1701.yugantaarbackend.services.core.events.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventsControllerImplementationTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventsControllerImplementation eventsController;

    private EventDTO eventDTO;
    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        eventDTO = new EventDTO(
                "Test Event",
                "A test event description",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                "Test Location",
                "Test Venue",
                100,
                true
        );

        event = new Event(); // Assuming `Event` has a default constructor
        event.setId(1L);
        event.setName("Test Event");
    }

    @Test
    void create_shouldReturnCreatedEvent() {
        when(eventService.registerEvent(eventDTO)).thenReturn(event);

        ResponseEntity<Event> response = eventsController.create(eventDTO);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test Event", response.getBody().getName());
        verify(eventService, times(1)).registerEvent(eventDTO);
    }

    @Test
    void getAll_shouldReturnListOfEvents() {
        when(eventService.getAll()).thenReturn(List.of(event));

        ResponseEntity<List<Event>> response = eventsController.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(eventService, times(1)).getAll();
    }

    @Test
    void getById_shouldReturnEventIfFound() {
        when(eventService.findById(1L)).thenReturn(Optional.of(event));

        ResponseEntity<Event> response = eventsController.getById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Event", response.getBody().getName());
        verify(eventService, times(1)).findById(1L);
    }

    @Test
    void getById_shouldReturnNotFoundIfEventDoesNotExist() {
        when(eventService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Event> response = eventsController.getById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(eventService, times(1)).findById(1L);
    }

    @Test
    void update_shouldReturnUpdatedEvent() {
        when(eventService.findById(1L)).thenReturn(Optional.of(event));
        when(eventService.update(1L, event)).thenReturn(Optional.of(event));

        ResponseEntity<Event> response = eventsController.update(1L, event);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(eventService, times(1)).update(1L, event);
    }

    @Test
    void update_shouldReturnNotFoundIfEventDoesNotExist() {
        when(eventService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Event> response = eventsController.update(1L, event);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(eventService, never()).update(1L, event);
    }

    @Test
    void delete_shouldReturnNoContentIfEventDeleted() {
        when(eventService.delete(1L)).thenReturn(true);

        ResponseEntity<Void> response = eventsController.delete(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(eventService, times(1)).delete(1L);
    }

    @Test
    void delete_shouldReturnNotFoundIfEventDoesNotExist() {
        when(eventService.delete(1L)).thenReturn(false);

        ResponseEntity<Void> response = eventsController.delete(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(eventService, times(1)).delete(1L);
    }

    @Test
    void search_shouldReturnPaginatedEvents() {
        Page<Event> eventPage = new PageImpl<>(List.of(event));
        when(eventService.search(PageRequest.of(0, 10))).thenReturn(eventPage);

        ResponseEntity<Page<Event>> response = eventsController.search(0, 10, null);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(eventService, times(1)).search(PageRequest.of(0, 10));
    }

    @Test
    void getByName_shouldReturnEventIfFound() {
        when(eventService.findByName("Test Event")).thenReturn(Optional.of(event));

        ResponseEntity<Event> response = eventsController.getByName("Test Event");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Event", response.getBody().getName());
        verify(eventService, times(1)).findByName("Test Event");
    }

    @Test
    void getByName_shouldReturnNotFoundIfEventDoesNotExist() {
        when(eventService.findByName("Test Event")).thenReturn(Optional.empty());

        ResponseEntity<Event> response = eventsController.getByName("Test Event");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(eventService, times(1)).findByName("Test Event");
    }
}
