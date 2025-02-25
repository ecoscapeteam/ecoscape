package com.java2024.ecoscape.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListingRulesDTO {

    private String rulesText;

    @NotNull(message = "Check-in time cannot be null")
    private String checkInTime;

    @NotNull(message = "Check-out time cannot be null")
    private String checkOutTime;

    private String quiteHoursStart;

    private String quiteQuiteHoursStop;

    private Boolean isSmokingAllowed;

    private Boolean isPartyingAllowed;

    private Boolean isLoudMusicAllowed;

    private Boolean ArePetsAllowed;

    private Boolean isSelfCheckingPossible;

    private Boolean isIdRequiredUponCheckin;

    public String getRulesText() {
        return rulesText;
    }

    public void setRulesText(String rulesText) {
        this.rulesText = rulesText;
    }

    public @NotNull(message = "Check-in time cannot be null") String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(@NotNull(message = "Check-in time cannot be null") String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public @NotNull(message = "Check-out time cannot be null") String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(@NotNull(message = "Check-out time cannot be null") String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getQuiteHoursStart() {
        return quiteHoursStart;
    }

    public void setQuiteHoursStart(String quiteHoursStart) {
        this.quiteHoursStart = quiteHoursStart;
    }

    public String getQuiteQuiteHoursStop() {
        return quiteQuiteHoursStop;
    }


    public Boolean getSmokingAllowed() {
        return isSmokingAllowed;
    }

    public void setSmokingAllowed(Boolean smokingAllowed) {
        isSmokingAllowed = smokingAllowed;
    }

    public Boolean getPartyingAllowed() {
        return isPartyingAllowed;
    }

    public void setPartyingAllowed(Boolean partyingAllowed) {
        isPartyingAllowed = partyingAllowed;
    }

    public Boolean getLoudMusicAllowed() {
        return isLoudMusicAllowed;
    }

    public void setLoudMusicAllowed(Boolean loudMusicAllowed) {
        isLoudMusicAllowed = loudMusicAllowed;
    }

    public Boolean getArePetsAllowed() {
        return ArePetsAllowed;
    }

    public void setArePetsAllowed(Boolean arePetsAllowed) {
        ArePetsAllowed = arePetsAllowed;
    }

    public Boolean getSelfCheckingPossible() {
        return isSelfCheckingPossible;
    }

    public void setSelfCheckingPossible(Boolean selfCheckingPossible) {
        isSelfCheckingPossible = selfCheckingPossible;
    }

    public Boolean getIdRequiredUponCheckin() {
        return isIdRequiredUponCheckin;
    }

    public void setIdRequiredUponCheckin(Boolean idRequiredUponCheckin) {
        isIdRequiredUponCheckin = idRequiredUponCheckin;
    }
}
