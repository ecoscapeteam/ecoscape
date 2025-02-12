package com.java2024.ecoscape.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
    private String quiteHoursStart;

    //tiden ska skrivas in inom en viss format, t.ex 11:00, 12:00, 00:00
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):00$",
            message = "Invalid time format! The time must be in the format HH:00, where HH is between 00 and 23.")
    private String quiteQuiteHoursStop;

    private Boolean isSmokingAllowed;

    private Boolean isPartyingAllowed;

    private Boolean isLoudMusicAllowed;

    private Boolean ArePetsAllowed;

    private Boolean isSelfCheckingPossible;

    private Boolean isIdRequiredUponCheckin;

}
