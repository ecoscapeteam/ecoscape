package com.java2024.ecoscape.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="payments")
public class Payment {
    @Id // specify field as PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @NotNull(message = "The required amount")
    @Positive(message = "The amount must be a positive number")
    private Long amount; // amount to be paid

    @PositiveOrZero(message = "The remaining amount cannot be negative")
    private  Long remainingAmount; // the remaining amount, if anu مبلغ باقي ان وجد

    @NotBlank(message = "Required currency")
    private String currency; // kr

    @NotNull(message = "Required Payment Type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; // export paymenttyp enum

    @NotNull(message = "Required Payment Status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // export PaymentStatus enum

    @NotBlank(message = "Stripe Payment ID required")
    private String paymentIntentId; // this Id come from Stripe , sometimes come with alfa or number, important be String

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY) // many payment can be for one booking
    @JoinColumn(name="booking_id")
    @JsonIgnore
    private Booking booking;


    @ManyToOne(fetch = FetchType.LAZY) // each payment belongs to one user, but a user can have many payment
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Long remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
