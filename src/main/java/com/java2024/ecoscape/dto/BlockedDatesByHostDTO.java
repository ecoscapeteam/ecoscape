package com.java2024.ecoscape.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class BlockedDatesByHostDTO {


    @NotNull(message = "Listing id can not be null")
    private Long listingId;

    @NotNull(message = "Start date of blocked dates can not be null")
    private Date startDate;

    @NotNull(message = "End date of blocked dates can not be null")
    private Date endDate;

    public BlockedDatesByHostDTO(Long listingId, Date startDate, Date endDate) {
        this.listingId = listingId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public @NotNull(message = "Listing id can not be null") Long getListingId() {
        return listingId;
    }

    public void setListingId(@NotNull(message = "Listing id can not be null") Long listingId) {
        this.listingId = listingId;
    }

    public @NotNull(message = "Start date of blocked dates can not be null") Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "Start date of blocked dates can not be null") Date startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "End date of blocked dates can not be null") Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "End date of blocked dates can not be null") Date endDate) {
        this.endDate = endDate;
    }
}
