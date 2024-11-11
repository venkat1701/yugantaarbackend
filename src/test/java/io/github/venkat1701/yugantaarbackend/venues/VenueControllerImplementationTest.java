package io.github.venkat1701.yugantaarbackend.venues;


import io.github.venkat1701.yugantaarbackend.controllers.implementation.venues.VenueControllerImplementation;
import io.github.venkat1701.yugantaarbackend.dto.venues.VenueDTO;
import io.github.venkat1701.yugantaarbackend.models.venues.Venue;
import io.github.venkat1701.yugantaarbackend.services.core.venues.VenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VenueControllerImplementationTest {

    @Mock
    private VenueService venueService;

    @InjectMocks
    private VenueControllerImplementation venueController;

    private VenueDTO sampleVenueDTO;
    private Venue sampleVenue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleVenueDTO = new VenueDTO("Venue 1", "123 Main St", 100, "Description", "John Doe", "johndoe@example.com", "1234567890");
        sampleVenue = new Venue();
        sampleVenue.setId(1L);
        sampleVenue.setName("Venue 1");
        sampleVenue.setAddress("123 Main St");
        sampleVenue.setCapacity(100);
        sampleVenue.setDescription("Description");
        sampleVenue.setContactPerson("John Doe");
        sampleVenue.setContactEmail("johndoe@example.com");
        sampleVenue.setContactPhone("1234567890");
    }

    @Test
    void testCreate() {
        when(venueService.registerVenue(sampleVenueDTO)).thenReturn(sampleVenue);

        ResponseEntity<Venue> response = venueController.create(sampleVenueDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleVenue, response.getBody());
        verify(venueService, times(1)).registerVenue(sampleVenueDTO);
    }

    @Test
    void testGetAll() {
        List<Venue> venues = Arrays.asList(sampleVenue);
        when(venueService.getAll()).thenReturn(venues);

        ResponseEntity<List<Venue>> response = venueController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venues, response.getBody());
        verify(venueService, times(1)).getAll();
    }

    @Test
    void testGetById_Found() {
        when(venueService.findById(1L)).thenReturn(Optional.of(sampleVenue));

        ResponseEntity<Venue> response = venueController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleVenue, response.getBody());
        verify(venueService, times(2)).findById(1L); // Called twice in controller
    }

    @Test
    void testGetById_NotFound() {
        when(venueService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Venue> response = venueController.getById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(venueService, times(1)).findById(1L);
    }

    @Test
    void testUpdate_Found() {
        when(venueService.findById(1L)).thenReturn(Optional.of(sampleVenue));
        when(venueService.update(1L, sampleVenue)).thenReturn(Optional.of(sampleVenue));

        ResponseEntity<Venue> response = venueController.update(1L, sampleVenue);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleVenue, response.getBody());
        verify(venueService, times(1)).update(1L, sampleVenue);
    }

    @Test
    void testUpdate_NotFound() {
        when(venueService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Venue> response = venueController.update(1L, sampleVenue);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(venueService, never()).update(1L, sampleVenue);
    }

    @Test
    void testDelete_Found() {
        when(venueService.findById(1L)).thenReturn(Optional.of(sampleVenue));

        ResponseEntity<Void> response = venueController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(venueService, times(1)).delete(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(venueService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = venueController.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(venueService, never()).delete(1L);
    }

    @Test
    void testSearch() {
        Page<Venue> venuePage = new PageImpl<>(Arrays.asList(sampleVenue));
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(venueService.search(pageRequest)).thenReturn(venuePage);

        ResponseEntity<Page<Venue>> response = venueController.search(0, 10, "");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venuePage, response.getBody());
        verify(venueService, times(1)).search(pageRequest);
    }
}
