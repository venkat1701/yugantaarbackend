package io.github.venkat1701.yugantaarbackend.dto.events;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Represents a Data Transfer Object for events in the system.
 * This class is used to encapsulate data related to an event,
 * ensuring that information can be transferred between layers
 * in the application efficiently.
 *
 * <p>Includes event details such as the event's name, description,
 * start and end dates, location, venue details, ticket price, and
 * whether the event is featured.</p>
 *
 * <p><strong>Author:</strong> Venkat</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    /** The name of the event. */
    @NotBlank(message = "Event name must not be blank")
    @Size(max = 255, message = "Event name must not exceed 255 characters")
    private String name;

    /** A brief description of the event. */
    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    /** The start date and time of the event. */
    @NotNull(message = "Start date must not be null")
    private LocalDateTime startDate;

    /** The end date and time of the event. */
    @NotNull(message = "End date must not be null")
    @FutureOrPresent(message = "End date must be in the present or future")
    private LocalDateTime endDate;

    /** The location where the event is being held. */
    @NotBlank(message = "Location must not be blank")
    @Size(max = 255, message = "Location must not exceed 255 characters")
    private String location;

    /** The name of the venue hosting the event. */
    @NotBlank(message = "Venue name must not be blank")
    @Size(max = 255, message = "Venue name must not exceed 255 characters")
    private String venueName;

    /** The ticket price for the event. */
    @PositiveOrZero(message = "Ticket price must be zero or positive")
    private int ticketPrice;

    /** Indicates whether the event is featured or not. */
    private boolean isFeatured;
}
