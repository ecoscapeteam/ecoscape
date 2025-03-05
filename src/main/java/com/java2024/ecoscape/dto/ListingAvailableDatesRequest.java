package com.java2024.ecoscape.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ListingAvailableDatesRequest {


    @NotNull(message = "Start date of available dates can not be null")
    private LocalDate startDate;

    @NotNull(message = "End date of available dates can not be null")
    private LocalDate endDate;

    public ListingAvailableDatesRequest() {
    }


    public @NotNull(message = "Start date of available dates can not be null") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "Start date of available dates can not be null") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "End date of available dates can not be null") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "End date of available dates can not be null") LocalDate endDate) {
        this.endDate = endDate;
    }


}
