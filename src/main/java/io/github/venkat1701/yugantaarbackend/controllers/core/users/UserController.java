package io.github.venkat1701.yugantaarbackend.controllers.core.users;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserController<MODEL, DTO, ID>{


    /**
     * Retrieve all resources
     * @return ResponseEntity containing list of all resources
     */
    @GetMapping
    ResponseEntity<List<DTO>> getAll();

    /**
     * Retrieve a resource by its ID
     * @param id the resource ID
     * @return ResponseEntity containing the found resource
     */
    @GetMapping("/{id}")
    ResponseEntity<DTO> getById(@PathVariable ID id);

    /**
     * Update an existing resource
     * @param id the resource ID
     * @param entity the updated resource data
     * @return ResponseEntity containing the updated resource
     */
    @PutMapping("/{id}")
    ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO entity);

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
    ResponseEntity<Page<DTO>> search(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    );
}
