package io.github.venkat1701.yugantaarbackend.exceptions.core;

/**
 * The {@code BaseDomainModelException} class is a custom exception designed to handle
 * error scenarios that are specific to domain model operations within an application.
 * This class extends {@code Exception}, making it a checked exception that must
 * be declared or handled within the code.
 *
 * <p>This class provides constructors to support error messages and optional causes
 * (root exceptions) that can assist in debugging complex domain-related errors.
 * By using this exception, developers can handle domain-specific issues more precisely,
 * offering better insight and clarity in the error-handling process.
 *
 * <p>Typical usage might include throwing this exception when there is an issue
 * within a domain model, such as invalid data encountered during model validation
 * or persistence issues specific to domain rules.
 *
 * <p>Example:
 * <pre>
 *     if (entity == null) {
 *         throw new BaseDomainModelException("Domain entity cannot be null.");
 *     }
 * </pre>
 *
 * <p>This exception class is intended for use as a base class for more specific
 * domain exceptions, allowing application-specific exceptions to inherit from it
 * and maintain a consistent exception hierarchy.
 *
 * @author Venkat
 * @since 1.0
 */
public class BaseDomainModelException extends Exception {

    /**
     * Constructs a new {@code BaseDomainModelException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by
     *                the {@link #getMessage()} method).
     */
    public BaseDomainModelException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code BaseDomainModelException} with the specified detail message
     * and cause.
     *
     * <p>The cause provides more context on the origin of the error, allowing this
     * exception to wrap other exceptions that might have contributed to the domain
     * model failure.</p>
     *
     * @param message the detail message (which is saved for later retrieval by
     *                the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method). A {@code null} value is permitted,
     *                indicating that the cause is unknown or not provided.
     */
    public BaseDomainModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
