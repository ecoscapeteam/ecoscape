package com.java2024.ecoscape.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class BlockedDatesByHostRequest {
    @NotNull(message = "Start date of blocked dates can not be null")
    private Date startDate;

    @NotNull(message = "End date of blocked dates can not be null")
    private Date endDate;

    public BlockedDatesByHostRequest() {
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
