package com.java2024.ecoscape.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ListingAvailableDatesRequest {

    @NotNull(message = "Id of listing available dates by host can not be null")
    private Long id;

    @NotNull(message = "Listing id can not be null")
    private Long listingId;

    @NotNull(message = "Start date of available dates can not be null")
    private LocalDate startDate;

    @NotNull(message = "End date of available dates can not be null")
    private LocalDate endDate;

    public ListingAvailableDatesRequest() {
    }

    public @NotNull(message = "Listing id can not be null") Long getListingId() {
        return listingId;
    }

    public void setListingId(@NotNull(message = "Listing id can not be null") Long listingId) {
        this.listingId = listingId;
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

    public @NotNull(message = "Id of available dates by host can not be null") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "Id of available dates by host can not be null") Long id) {
        this.id = id;
    }
}
