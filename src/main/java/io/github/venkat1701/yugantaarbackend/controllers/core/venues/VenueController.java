package io.github.venkat1701.yugantaarbackend.controllers.core.venues;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/venues")
public interface VenueController<MODEL, DTO, ID> {
    @PostMapping("/create")
    ResponseEntity<MODEL> create(@RequestBody DTO entity);

    @GetMapping
    ResponseEntity<List<MODEL>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<MODEL> getById(@PathVariable ID id);

    @PutMapping("/{id}")
    ResponseEntity<MODEL> update(@PathVariable ID id, @RequestBody MODEL entity);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable ID id);

    @GetMapping("/search")
    ResponseEntity<Page<MODEL>> search(
            @RequestParam(required=false, defaultValue="0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    );

}
