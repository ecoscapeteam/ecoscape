package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingRequest {

    private String usersContactPhoneNumber;
    private String usersContactEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guests;

    public BookingRequest(String usersContactPhoneNumber, String usersContactEmail, LocalDate startDate, LocalDate endDate, int guests) {
        this.usersContactPhoneNumber = usersContactPhoneNumber;
        this.usersContactEmail = usersContactEmail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guests = guests;
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

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }



}



