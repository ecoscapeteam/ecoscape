package com.java2024.ecoscape.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "rules")
public class Rules {

    @Id
    @GeneratedValue
    private Long id;

    //rulestext är optional
    @Column(nullable = true)
    //ifall man skriver vill skriva text om regler så ska endast bokstäver, siffror, mellanslag och vissa skiljetecken som kommatecken, punkter och frågetecken tillåts
    @Pattern(regexp = "^[A-Za-z0-9\\s\\.,!?\'\"\\(\\)\\-\\&\\#\\*\\+\\=]*$",
            message = "Invalid rule description! Only letters, numbers, spaces, commas, periods, exclamation marks, question marks, and other specified characters are allowed.")
    @Length(max = 250, message = "Rules text can not exceed 255 characters")
    private String rulesText;

    //Incheckning tiden ska skrivas in inom en viss format, t.ex 11:00, 12:00, 00:00
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.")
    //Incheckning tiden inte vara null
    @NotNull(message = "Checkin time can not be null")
    private String checkInTime;

    ////Utcheckning tiden ska skrivas in inom en viss format, t.ex 11:00, 12:00, 00:00
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.")
    //Utcheckning tiden kan inte vara null
    @NotNull(message = "Checkout time can not be null")
    private String checkOutTime;

    //tiden ska skrivas in inom en viss format, t.ex 11:00, 12:00, 00:00
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.")

    //optional field, kan vara null
    @Column(nullable = true)
    private String quiteHoursStart;

    //tiden ska skrivas in inom en viss format, t.ex 11:00, 12:00, 00:00
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.")
    //optional field, kan vara null
    @Column(nullable = true)
    private String quiteQuiteHoursStop;

    //optional field, kan vara null
    @Column(nullable = true)
    private Boolean isSmokingAllowed;

    //optional field, kan vara null
    @Column(nullable = true)
    private Boolean isPartyingAllowed;

    //optional field, kan vara null
    @Column(nullable = true)
    private Boolean isLoudMusicAllowed;

    //optional field, kan vara null
    @Column(nullable = true)
    private Boolean ArePetsAllowed;

    //optional field, kan vara null
    @Column(nullable = true)
    private Boolean isSelfCheckingPossible;

    //optional field, kan vara null
    @Column(nullable = true)
    private Boolean isIdRequiredUponCheckin;

    public Rules() {
    }

    public @Pattern(regexp = "^[A-Za-z0-9\\s\\.,!?'\"\\(\\)\\-\\&\\#\\*\\+\\=]*$",
            message = "Invalid rule description! Only letters, numbers, spaces, commas, periods, exclamation marks, question marks, and other specified characters are allowed.") @Length(max = 250, message = "Rules text can not exceed 255 characters") String getRulesText() {
        return rulesText;
    }

    public void setRulesText(@Pattern(regexp = "^[A-Za-z0-9\\s\\.,!?'\"\\(\\)\\-\\&\\#\\*\\+\\=]*$",
            message = "Invalid rule description! Only letters, numbers, spaces, commas, periods, exclamation marks, question marks, and other specified characters are allowed.") @Length(max = 250, message = "Rules text can not exceed 255 characters") String rulesText) {
        this.rulesText = rulesText;
    }

    public @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.") @NotNull(message = "Checkin time can not be null") String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(@Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.") @NotNull(message = "Checkin time can not be null") String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.") @NotNull(message = "Checkout time can not be null") String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(@Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.") @NotNull(message = "Checkout time can not be null") String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.") String getQuiteHoursStart() {
        return quiteHoursStart;
    }

    public void setQuiteHoursStart(@Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.") String quiteHoursStart) {
        this.quiteHoursStart = quiteHoursStart;
    }

    public @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.") String getQuiteQuiteHoursStop() {
        return quiteQuiteHoursStop;
    }

    public void setQuiteQuiteHoursStop(@Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.") String quiteQuiteHoursStop) {
        this.quiteQuiteHoursStop = quiteQuiteHoursStop;
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