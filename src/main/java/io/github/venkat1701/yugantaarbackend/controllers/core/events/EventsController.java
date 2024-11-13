package io.github.venkat1701.yugantaarbackend.controllers.core.events;

import io.github.venkat1701.yugantaarbackend.dto.events.EventDTO;
import io.github.venkat1701.yugantaarbackend.models.events.Event;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EventsController<MODEL, DTO, ID> {

    ResponseEntity<DTO> create(@RequestBody DTO entity);

    ResponseEntity<List<DTO>> getAll();

    ResponseEntity<DTO> getById(@PathVariable ID id);

    ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO entity);

    ResponseEntity<Void> delete(@PathVariable ID id);

    ResponseEntity<Page<EventDTO>> search(@RequestParam int page, @RequestParam int size, @RequestParam String sort);

    ResponseEntity<DTO> getByName(@RequestParam String name);

}
