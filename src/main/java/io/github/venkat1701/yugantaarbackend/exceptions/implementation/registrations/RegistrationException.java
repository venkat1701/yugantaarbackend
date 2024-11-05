package io.github.venkat1701.yugantaarbackend.exceptions.implementation.registrations;

import io.github.venkat1701.yugantaarbackend.exceptions.core.BaseDomainModelException;

/**
 * The {@code RegistrationException} class is a custom exception designed to handle
 * errors related to user registration within the application. This exception extends
 * {@link BaseDomainModelException}, which serves as the base for domain-specific
 * exceptions, enabling structured handling of registration-related issues.
 *
 * <p>This exception is intended to be thrown in scenarios where user registration fails,
 * such as when required information is missing, validation rules are not met, or
 * duplicate accounts are detected.
 *
 * <p>By using this exception, developers can provide clear and specific error messages
 * for registration-related issues, improving the clarity of error handling and debugging.
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     if (username == null || username.isEmpty()) {
 *         throw new RegistrationException("Username cannot be empty during registration.");
 *     }
 * </pre>
 *
 * <p>This class includes constructors for specifying only a message or both a message and a cause,
 * allowing for flexible exception reporting based on the context of the failure.
 *
 * <p><b>Author:</b> Venkat
 *
 * @since 1.0
 */
public class RegistrationException extends BaseDomainModelException {

    /**
     * Constructs a new {@code RegistrationException} with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public RegistrationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code RegistrationException} with the specified detail message
     * and cause.
     *
     * <p>This constructor allows capturing the underlying reason for the registration failure,
     * which can aid in debugging by providing additional context in nested exception scenarios.</p>
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   the cause of the exception, saved for later retrieval by the {@link #getCause()} method.
     *                A {@code null} value is permitted, indicating that the cause is nonexistent or unknown.
     */
    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
