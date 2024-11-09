package io.github.venkat1701.yugantaarbackend.services.implementation.events;

import io.github.venkat1701.yugantaarbackend.services.core.commons.GenericCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public class EventsServiceImplementation implements GenericCrudService {
    @Override
    public Page search(PageRequest pageRequest) {
        return null;
    }

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public Optional findById(Object o) {
        return Optional.empty();
    }

    @Override
    public Optional update(Object o, Object user) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }
}
