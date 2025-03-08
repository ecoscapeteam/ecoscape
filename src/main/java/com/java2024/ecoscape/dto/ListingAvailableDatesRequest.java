package com.java2024.ecoscape.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ListingAvailableDatesRequest {


   // @NotNull(message = "Start date of available dates can not be null")
    private LocalDate startDate;

   // @NotNull(message = "End date of available dates can not be null")
    private LocalDate endDate;

    public ListingAvailableDatesRequest() {
    }



    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate( LocalDate startDate) {
        this.startDate = startDate;
    }

    public  LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate( LocalDate endDate) {
        this.endDate = endDate;
    }


}
