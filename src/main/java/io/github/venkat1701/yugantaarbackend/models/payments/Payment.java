package io.github.venkat1701.yugantaarbackend.models.payments;

import io.github.venkat1701.yugantaarbackend.models.events.Event;
import io.github.venkat1701.yugantaarbackend.models.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a payment transaction in the application.
 * <p>
 * This entity captures all relevant details about a payment,
 * including the associated user, event, payment amount,
 * payment method, and transaction status. It also tracks
 * timestamps for creation and updates of the payment record.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "payments")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Positive
    private int amount;

    @NotBlank
    @Column(name = "payment_status")
    private String paymentStatus;

    @NotBlank
    @Column(name = "payment_method")
    private String paymentMethod;

    @NotBlank
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "refund_status")
    private String refundStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Compares this Payment object to another object for equality.
     *
     * @param o the object to be compared
     * @return true if this Payment is the same as the other object, false otherwise
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Payment payment = (Payment) o;
        return getId() != null && Objects.equals(getId(), payment.getId());
    }

    /**
     * Returns a hash code value for this Payment object.
     *
     * @return a hash code value for this Payment
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
