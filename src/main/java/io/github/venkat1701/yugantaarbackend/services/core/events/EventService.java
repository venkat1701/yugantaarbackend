package io.github.venkat1701.yugantaarbackend.services.core.events;

import io.github.venkat1701.yugantaarbackend.models.events.Event;
import io.github.venkat1701.yugantaarbackend.services.core.commons.GenericCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public class EventService implements GenericCrudService<Event, Long> {
    @Override
    public Page<Event> search(PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<Event> getAll() {
        return List.of();
    }

    @Override
    public Optional<Event> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Event> update(Long aLong, Event user) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}
