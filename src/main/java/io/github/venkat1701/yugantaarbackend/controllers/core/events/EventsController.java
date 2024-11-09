package io.github.venkat1701.yugantaarbackend.controllers.core.events;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EventsController<MODEL, DTO, ID> {

    ResponseEntity<MODEL> create(@RequestBody DTO entity);

    ResponseEntity<List<MODEL>> getAll();

    ResponseEntity<MODEL> getById(@PathVariable ID id);

    ResponseEntity<MODEL> update(@PathVariable ID id, @RequestBody MODEL entity);

    ResponseEntity<Void> delete(@PathVariable ID id);

    ResponseEntity<List<MODEL>> search(@RequestParam int page, @RequestParam int size, @RequestParam String sort);

    ResponseEntity<MODEL> getByName(@RequestParam String name);

}
