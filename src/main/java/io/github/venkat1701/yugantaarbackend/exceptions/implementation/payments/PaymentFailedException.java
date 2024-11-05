package io.github.venkat1701.yugantaarbackend.exceptions.implementation.payments;

import io.github.venkat1701.yugantaarbackend.exceptions.core.BaseDomainModelException;

/**
 * The {@code PaymentFailedException} class is a custom exception designed to handle
 * payment-related failures within the application. Extending {@link BaseDomainModelException},
 * this exception allows the application to handle payment issues in a structured way,
 * making the error handling specific to payment processing failures.
 *
 * <p>This exception can be thrown in scenarios where a payment transaction fails,
 * such as when there is an issue with payment processing, insufficient funds, network
 * issues, or invalid payment credentials.
 *
 * <p>Using this exception allows developers to provide specific error messages and,
 * if needed, capture the underlying cause of the payment failure, enhancing traceability
 * in complex applications.
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     if (!paymentSuccessful) {
 *         throw new PaymentFailedException("Payment processing failed due to insufficient funds.");
 *     }
 * </pre>
 *
 * <p>This class supports constructors for creating instances with only a message or
 * with both a message and a cause, giving flexibility for different error contexts.
 *
 * <p><b>Author:</b> Venkat
 *
 * @since 1.0
 */
public class PaymentFailedException extends BaseDomainModelException {

    /**
     * Constructs a new {@code PaymentFailedException} with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public PaymentFailedException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code PaymentFailedException} with the specified detail message
     * and cause.
     *
     * <p>This constructor allows capturing the underlying reason for the payment failure,
     * making it easier to diagnose complex issues by preserving the cause of the failure.</p>
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   the cause of the exception, saved for later retrieval by the {@link #getCause()} method.
     *                A {@code null} value is permitted, indicating that the cause is nonexistent or unknown.
     */
    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
