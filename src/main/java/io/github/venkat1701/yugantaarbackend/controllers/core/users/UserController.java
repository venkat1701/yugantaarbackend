package io.github.venkat1701.yugantaarbackend.controllers.core.users;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserController<MODEL, DTO, ID>{
    /**
     * Create a new resource
     * @param entity the entity to create
     * @return ResponseEntity containing the created resource
     */
    @PostMapping
    ResponseEntity<MODEL> create(@RequestBody DTO entity);

    /**
     * Retrieve all resources
     * @return ResponseEntity containing list of all resources
     */
    @GetMapping
    ResponseEntity<List<MODEL>> getAll();

    /**
     * Retrieve a resource by its ID
     * @param id the resource ID
     * @return ResponseEntity containing the found resource
     */
    @GetMapping("/{id}")
    ResponseEntity<MODEL> getById(@PathVariable ID id);

    /**
     * Update an existing resource
     * @param id the resource ID
     * @param entity the updated resource data
     * @return ResponseEntity containing the updated resource
     */
    @PutMapping("/{id}")
    ResponseEntity<MODEL> update(@PathVariable ID id, @RequestBody MODEL entity);

    /**
     * Delete a resource by its ID
     * @param id the resource ID
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable ID id);

    /**
     * Retrieve paginated and sorted resources
     * @param page page number (optional)
     * @param size items per page (optional)
     * @param sort sort criteria (optional)
     * @return ResponseEntity containing page of resources
     */
    @GetMapping("/search")
    ResponseEntity<Page<MODEL>> search(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    );
}
