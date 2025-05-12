package com.java2024.ecoscape.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.java2024.ecoscape.models.PaymentStatus;
import com.java2024.ecoscape.models.PaymentType;
import com.stripe.model.PaymentIntent;

import java.time.LocalDateTime;


@JsonPropertyOrder({
        "id",
        "bookingId",
        "userId",
        "amount",
        "remainingAmount",
        "currency",
        "paymentType",
        "paymentStatus",
        "paymentIntentId",
        "createdAt"
})

public class PaymentResponse {
    private Long id;
    private Long bookingId;
    private Long userId;
    private Long amount;
    private Long remainingAmount;
    private String currency;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private String paymentIntentId;
    private LocalDateTime createdAt;
    private String message;


    private PaymentIntent paymentIntent;
    public PaymentResponse() {}

    // المُنشئ الذي يقبل الرسالة فقط
    public PaymentResponse(String message) {
        this.message = message;
    }

    public PaymentResponse(Long id, Long amount, Long remainingAmount, String currency, PaymentType paymentType,
                           PaymentStatus paymentStatus, String paymentIntentId, LocalDateTime createdAt,
                           Long bookingId,Long userId, String message) {
        this.id = id;
        this.bookingId = bookingId;
        this.userId = userId;
        this.amount = amount;
        this.remainingAmount = remainingAmount;
        this.currency = currency;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.paymentIntentId = paymentIntentId;
        this.createdAt = createdAt;
        this.message = message;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaymentIntent getPaymentIntent() {
        return paymentIntent;
    }

    public void setPaymentIntent(PaymentIntent paymentIntent) {
        this.paymentIntent = paymentIntent;
    }
}


