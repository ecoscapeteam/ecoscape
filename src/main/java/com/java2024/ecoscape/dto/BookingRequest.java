package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingRequest {
    private BookingDTO bookingDto;
    private Long userId;
    private Long listingId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private int guests;
    private BigDecimal totalPrice;
    private BigDecimal websitesFee;

    public BookingDTO getBookingDto() {
        return bookingDto;
    }

    public void setBookingDto(BookingDTO bookingDto) {
        this.bookingDto = bookingDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public BigDecimal getWebsitesFee() {
        return websitesFee;
    }

    public void setWebsitesFee(BigDecimal websitesFee) {
        this.websitesFee = websitesFee;
    }
}
