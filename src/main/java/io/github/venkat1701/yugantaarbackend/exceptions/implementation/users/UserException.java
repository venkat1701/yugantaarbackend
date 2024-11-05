package io.github.venkat1701.yugantaarbackend.exceptions.implementation.users;

import io.github.venkat1701.yugantaarbackend.exceptions.core.BaseDomainModelException;

/**
 * The {@code UserException} class is a custom exception for handling user-specific
 * error scenarios within an application. This exception extends {@link BaseDomainModelException},
 * allowing for a consistent hierarchy of domain-related exceptions.
 *
 * <p>This class enables developers to specify a custom message and an optional cause
 * for exceptions that are specific to user-related issues, such as invalid user input
 * or failed user actions due to application-specific constraints.
 *
 * <p>By leveraging this exception, developers can ensure that user-related errors are
 * captured in a meaningful way, providing a clear message and, if necessary, details
 * on the underlying cause.
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     if (user == null) {
 *         throw new UserException("User not found.");
 *     }
 * </pre>
 *
 * <p>This class utilizes the {@link #getMessage()} and {@link #getCause()} methods from
 * the {@code Throwable} superclass to retrieve error details and the root cause.
 *
 * @see BaseDomainModelException
 * @see Exception
 * @see Throwable
 *
 * <p>Note: The {@code message} field here is inherited from the superclass, and additional
 * custom handling can be implemented as needed.
 *
 * <p>This exception is part of the {@code yugantaarbackend} package, dedicated to managing
 * user-related errors within the broader application error-handling structure.
 *
 * <p><b>Author:</b> Venkat
 *
 * @since 1.0
 */
public class UserException extends BaseDomainModelException {

    /**
     * Constructs a new {@code UserException} with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval by
     *                the {@link #getMessage()} method.
     */
    public UserException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UserException} with the specified detail message
     * and cause.
     *
     * <p>The {@code cause} parameter is useful for capturing the underlying reason
     * for this exception, particularly in cases where this exception is thrown in
     * response to a lower-level exception.
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   the cause, saved for later retrieval by the {@link #getCause()} method. A {@code null}
     *                value is permitted, indicating that the cause is nonexistent or unknown.
     */
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
