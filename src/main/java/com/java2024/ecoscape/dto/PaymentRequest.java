package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.PaymentStatus;
import com.java2024.ecoscape.models.PaymentType;


import java.time.LocalDateTime;

public class PaymentRequest {

    private Long bookingId;
    private Long userId;
    private Long amount; // amount to be paid
    private  Long remainingAmount; // the remaining amount, if anu مبلغ باقي ان وجد
    private String currency; // kr
    private PaymentType paymentType; // export paymenttyp enum
    private PaymentStatus paymentStatus; // export PaymentStatus enum
    private LocalDateTime createdAt;
    private String paymentIntentId;

    public PaymentRequest() {

    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }
}
