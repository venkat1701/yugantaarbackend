package io.github.venkat1701.yugantaarbackend.models.payments;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int userId;

    private int eventId;

    private int amount;
    private String paymentStatus;
    private String paymentMethod;
    private String transactionId;
    private Date paymentDate;

    private String refundStatus;
    private String createdAt;
    private String updatedAt;
}
