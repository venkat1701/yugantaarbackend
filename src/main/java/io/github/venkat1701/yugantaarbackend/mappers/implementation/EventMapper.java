package io.github.venkat1701.yugantaarbackend.mappers.implementation;

import io.github.venkat1701.yugantaarbackend.dto.events.EventDTO;
import io.github.venkat1701.yugantaarbackend.mappers.core.GenericMapper;
import io.github.venkat1701.yugantaarbackend.models.events.Event;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventMapper implements GenericMapper<EventDTO, Event> {
    @Override
    public EventDTO toDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setFeatured(event.isFeatured());
        dto.setLocation(event.getLocation());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setTicketPrice(event.getTicketPrice());
        dto.setVenueName(event.getVenueName());

        return dto;
    }

    @Override
    public Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setFeatured(eventDTO.isFeatured());
        event.setLocation(eventDTO.getLocation());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setTicketPrice(eventDTO.getTicketPrice());
        event.setVenueName(eventDTO.getVenueName());
        event.setUpdatedAt(LocalDateTime.now());
        return event;
    }
}
