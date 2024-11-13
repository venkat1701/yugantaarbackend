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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class EventsControllerImplementationSecurityTest {

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

        event = new Event();
        event.setId(1L);
        event.setName("Test Event");
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void create_shouldAllowManagerToCreateEvent() {
        when(eventService.registerEvent(eventDTO)).thenReturn(eventDTO);

        ResponseEntity<EventDTO> response = eventsController.create(eventDTO);

        assertEquals(403, response.getStatusCodeValue());  // Forbidden for guests
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_shouldAllowAdminToViewAllEvents() {
        when(eventService.getAll()).thenReturn(List.of(eventDTO));

        ResponseEntity<List<EventDTO>> response = eventsController.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(eventService, times(1)).getAll();
    }

    @Test
    @WithMockUser(roles = "PARTICIPANT")
    void getAll_shouldNotAllowParticipantToViewAllEvents() {
        ResponseEntity<List<EventDTO>> response = eventsController.getAll();

        assertEquals(403, response.getStatusCodeValue());  // Forbidden for participants
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void update_shouldAllowManagerToUpdateEvent() {
        when(eventService.findById(1L)).thenReturn(Optional.of(eventDTO));
        when(eventService.update(1L, eventDTO)).thenReturn(Optional.of(eventDTO));

        ResponseEntity<EventDTO> response = eventsController.update(1L, eventDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(eventService, times(1)).update(1L, eventDTO);
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void update_shouldNotAllowGuestToUpdateEvent() {
        ResponseEntity<EventDTO> response = eventsController.update(1L, eventDTO);

        assertEquals(403, response.getStatusCodeValue());  // Forbidden for guests
    }

    @Test
    @WithMockUser(roles = "SUPERADMIN")
    void delete_shouldAllowSuperAdminToDeleteEvent() {
        when(eventService.delete(1L)).thenReturn(true);

        ResponseEntity<Void> response = eventsController.delete(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(eventService, times(1)).delete(1L);
    }

    @Test
    @WithMockUser(roles = "PARTICIPANT")
    void delete_shouldNotAllowParticipantToDeleteEvent() {
        ResponseEntity<Void> response = eventsController.delete(1L);

        assertEquals(403, response.getStatusCodeValue());  // Forbidden for participants
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void search_shouldAllowAdminToSearchEvents() {
        when(eventService.search(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(eventDTO)));

        ResponseEntity<Page<EventDTO>> response = eventsController.search(0, 10, null);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(eventService, times(1)).search(PageRequest.of(0, 10));
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void search_shouldNotAllowGuestToSearchEvents() {
        ResponseEntity<Page<EventDTO>> response = eventsController.search(0, 10, null);

        assertEquals(403, response.getStatusCodeValue());  // Forbidden for guests
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getByName_shouldAllowAdminToGetEventByName() {
        when(eventService.findByName("Test Event")).thenReturn(Optional.of(eventDTO));

        ResponseEntity<EventDTO> response = eventsController.getByName("Test Event");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Event", response.getBody().getName());
        verify(eventService, times(1)).findByName("Test Event");
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void getByName_shouldAllowGuestToGetEventByName() {
        when(eventService.findByName("Test Event")).thenReturn(Optional.of(eventDTO));

        ResponseEntity<EventDTO> response = eventsController.getByName("Test Event");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Event", response.getBody().getName());
        verify(eventService, times(1)).findByName("Test Event");
    }

    @Test
    @WithMockUser(roles = "PARTICIPANT")
    void getById_shouldNotAllowParticipantToGetEventById() {
        when(eventService.findById(1L)).thenReturn(Optional.of(eventDTO));

        ResponseEntity<EventDTO> response = eventsController.getById(1L);

        assertEquals(403, response.getStatusCodeValue());  // Forbidden for participants
    }
}
