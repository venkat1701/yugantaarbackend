package io.github.venkat1701.yugantaarbackend.controllers.implementation.venues;

import io.github.venkat1701.yugantaarbackend.controllers.core.venues.VenueController;
import io.github.venkat1701.yugantaarbackend.dto.venues.VenueDTO;
import io.github.venkat1701.yugantaarbackend.models.venues.Venue;
import io.github.venkat1701.yugantaarbackend.services.core.venues.VenueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/venues")
public class VenueControllerImplementation implements VenueController<Venue, VenueDTO, Long> {

    private final VenueService venueService;

    public VenueControllerImplementation(VenueService venueService) {
        this.venueService = venueService;
    }


    @Override
    @PostMapping("/create")
    public ResponseEntity<Venue> create(VenueDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.venueService.registerVenue(entity));
    }

    @Override
    @PostMapping("/all")
    public ResponseEntity<List<Venue>> getAll() {
        return ResponseEntity.ok(this.venueService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Venue> getById(Long aLong) {
        if(this.venueService.findById(aLong).isPresent()) {
            return ResponseEntity.ok(this.venueService.findById(aLong).get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Venue> update(Long aLong, Venue entity) {
        if(this.venueService.findById(aLong).isPresent()) {
            return ResponseEntity.ok(this.venueService.update(aLong, entity).get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long aLong) {
        if(this.venueService.findById(aLong).isPresent()) {
            this.venueService.delete(aLong);
            return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<Venue>> search(int page, int size, String sort) {
        PageRequest pageRequest;
        if (sort != null && !sort.isEmpty()) {
            String[] sortParameters = sort.split(",");
            Sort.Direction direction = sortParameters.length > 1?
                    Sort.Direction.fromString(sortParameters[1]) : Sort.Direction.ASC;
            pageRequest = PageRequest.of(page, size, Sort.by(direction, sortParameters[0]));
        } else {
            pageRequest = PageRequest.of(page, size);
        }
        Page<Venue> venues = this.venueService.search(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(venues);
    }
}
