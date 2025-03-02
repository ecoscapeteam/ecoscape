package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingResponse {

    private Long bookingId;
    private Long userId;
    private Long listingId;
    private String firstName;
    private String lastName;
    private String usersContactPhoneNumber;
    private String usersContactEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private int guests;
    private BigDecimal pricePerNight;
    private BigDecimal website_Fee;
    private BigDecimal cleaningFee;
    private BigDecimal totalPrice;

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
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

    public String getUsersContactPhoneNumber() {
        return usersContactPhoneNumber;
    }

    public void setUsersContactPhoneNumber(String usersContactPhoneNumber) {
        this.usersContactPhoneNumber = usersContactPhoneNumber;
    }

    public String getUsersContactEmail() {
        return usersContactEmail;
    }

    public void setUsersContactEmail(String usersContactEmail) {
        this.usersContactEmail = usersContactEmail;
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

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public BigDecimal getWebsite_Fee() {
        return website_Fee;
    }

    public void setWebsite_Fee(BigDecimal website_Fee) {
        this.website_Fee = website_Fee;
    }

    public BigDecimal getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(BigDecimal cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}