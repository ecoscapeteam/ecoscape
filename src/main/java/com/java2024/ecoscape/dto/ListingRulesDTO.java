package com.java2024.ecoscape.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private Boolean arePetsAllowed;

    private Boolean isSelfCheckingPossible;

    private Boolean isIdRequiredUponCheckIn;

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

    public void setQuiteQuiteHoursStop(String quiteQuiteHoursStop) {
        this.quiteQuiteHoursStop = quiteQuiteHoursStop;
    }

    @JsonProperty("isSmokingAllowed")
    public Boolean getSmokingAllowed() {
        return isSmokingAllowed;
    }

    @JsonProperty("isSmokingAllowed")
    public void setSmokingAllowed(Boolean smokingAllowed) {
        isSmokingAllowed = smokingAllowed;
    }

    @JsonProperty("isPartyingAllowed")
    public Boolean getPartyingAllowed() {
        return isPartyingAllowed;
    }

    @JsonProperty("isPartyingAllowed")
    public void setPartyingAllowed(Boolean partyingAllowed) {
        isPartyingAllowed = partyingAllowed;
    }

    @JsonProperty("isLoudMusicAllowed")
    public Boolean getLoudMusicAllowed() {
        return isLoudMusicAllowed;
    }

    @JsonProperty("isLoudMusicAllowed")
    public void setLoudMusicAllowed(Boolean loudMusicAllowed) {
        isLoudMusicAllowed = loudMusicAllowed;
    }

    @JsonProperty("arePetsAllowed")
    public Boolean getArePetsAllowed() {
        return arePetsAllowed;
    }

    @JsonProperty("arePetsAllowed")
    public void setArePetsAllowed(Boolean arePetsAllowed) {
        this.arePetsAllowed = arePetsAllowed;
    }

    @JsonProperty("isSelfCheckingPossible")
    public Boolean getSelfCheckingPossible() {
        return isSelfCheckingPossible;
    }

    @JsonProperty("isSelfCheckingPossible")
    public void setSelfCheckingPossible(Boolean selfCheckingPossible) {
        isSelfCheckingPossible = selfCheckingPossible;
    }

    @JsonProperty("isIdRequiredUponCheckIn")
    public Boolean getIdRequiredUponCheckIn() {
        return isIdRequiredUponCheckIn;
    }

    @JsonProperty("isIdRequiredUponCheckIn")
    public void setIdRequiredUponCheckIn(Boolean idRequiredUponCheckIn) {
        isIdRequiredUponCheckIn = idRequiredUponCheckIn;
    }
}
