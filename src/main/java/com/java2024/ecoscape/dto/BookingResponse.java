package com.java2024.ecoscape.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.java2024.ecoscape.models.Booking;
import com.java2024.ecoscape.models.Payment;
import com.java2024.ecoscape.models.Status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@JsonPropertyOrder({ "message", "bookingId", "userId", "listingId", "firstName", "lastName",
        "usersContactPhoneNumber", "usersContactEmail", "startDate", "endDate",
        "status", "guests", "pricePerNight", "websiteFee", "cleaningFee", "totalPrice" })

public class BookingResponse {

    private Long bookingId;
    private Long userId;
    private Long listingId;
    private String listingname;
    private String firstName;
    private String lastName;
    private String usersContactPhoneNumber;
    private String usersContactEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private Integer guests;
    private BigDecimal pricePerNight;
    private BigDecimal websiteFee;
    private BigDecimal cleaningFee;
    private BigDecimal totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @Override
    public String toString() {
        return "Booking Details:\n" +
                "------------------------------\n" +
                "Booking ID: " + bookingId + "\n" +
                "Listing ID: " + listingId + "\n" +
                "Guest Name: " + firstName + " " + lastName + "\n" +
                "Contact Email: " + usersContactEmail + "\n" +
                "Contact Phone: " + usersContactPhoneNumber + "\n" +
                "Check-in Date: " + startDate + "\n" +
                "Check-out Date: " + endDate + "\n" +
                "Guests: " + guests + "\n" +
                "Price per Night: " + pricePerNight + " SEK\n" +
                "Website Fee: " + websiteFee + " SEK\n" +
                "Cleaning Fee: " + cleaningFee + " SEK\n" +
                "Total Price: " + totalPrice + " SEK\n" +
                "Status: " + status + "\n" ;

    }


    public BookingResponse() {
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

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public BigDecimal getWebsiteFee() {
        return websiteFee;
    }

    public void setWebsiteFee(BigDecimal websiteFee) {
        this.websiteFee = websiteFee;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getListingname() {
        return listingname;
    }

    public void setListingname(String listingname) {
        this.listingname = listingname;
    }
}
