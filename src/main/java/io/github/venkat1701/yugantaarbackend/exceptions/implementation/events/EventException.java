package io.github.venkat1701.yugantaarbackend.exceptions.implementation.events;

import io.github.venkat1701.yugantaarbackend.exceptions.core.BaseDomainModelException;

/**
 * The {@code EventException} class is a custom exception for handling event-related
 * errors within an application. This exception extends {@link BaseDomainModelException},
 * which provides a consistent exception hierarchy across domain-specific issues.
 *
 * <p>This class provides constructors that accept a message and an optional cause,
 * allowing developers to specify detailed information about the error and, if necessary,
 * capture the underlying cause of the event issue.
 *
 * <p>Typical usage might include throwing this exception when an event cannot be processed,
 * an event validation fails, or a scheduling conflict occurs.
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     if (event == null) {
 *         throw new EventException("Event not found.");
 *     }
 * </pre>
 *
 * <p>Using this exception provides clear, organized handling for event-related failures,
 * improving maintainability and debugging efforts.
 *
 * <p><b>Author:</b> Venkat
 *
 * @since 1.0
 */
public class EventException extends BaseDomainModelException {

    /**
     * Constructs a new {@code EventException} with the specified detail message.
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     */
    public EventException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code EventException} with the specified detail message
     * and cause.
     *
     * <p>This constructor allows capturing the root cause of the event exception,
     * enabling better traceability in nested exception scenarios.</p>
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   the cause of the exception, saved for later retrieval by the {@link #getCause()} method.
     *                A {@code null} value is permitted, indicating that the cause is nonexistent or unknown.
     */
    public EventException(String message, Throwable cause) {
        super(message, cause);
    }
}

