package io.github.venkat1701.yugantaarbackend.controllers.implementation.events;

import io.github.venkat1701.yugantaarbackend.controllers.core.events.EventsController;
import io.github.venkat1701.yugantaarbackend.dto.events.EventDTO;
import io.github.venkat1701.yugantaarbackend.models.events.Event;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class EventsControllerImplementation implements EventsController<Event, EventDTO, Long> {


    @Override
    public ResponseEntity<Event> create(EventDTO entity) {
        return null;
    }

    @Override
    public ResponseEntity<List<Event>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<Event> getById(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<Event> update(Long aLong, Event entity) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<List<Event>> search(int page, int size, String sort) {
        return null;
    }

    @Override
    public ResponseEntity<Event> getByName(String name) {
        return null;
    }
}
