package com.java2024.ecoscape.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "blocked_dates_by_host")
public class BlockedDatesByHost {

    @Id
    @GeneratedValue
    private Long id;

    // blocked dates by host är many to one gentemot listing, en listing kan ha flera blocked dates by host
    // "laddar" hela allt data från rules table
    @ManyToOne(fetch = FetchType.EAGER)
    //listing_id är FOREIGN KEY här, refererar till id i listing tabellen
    @JoinColumn(name = "listing_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Listing can not be null")
    private Listing listing;

    @NotNull(message = "Start date can not be null")
    private Date startDate;

    @NotNull(message = "End date can not be null")
    private Date endDate;

    //default konstruktor som JPA kräver
    public BlockedDatesByHost() {
    }
//kontruktor som behövs för att sätt start och end date
    public BlockedDatesByHost(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //annotation PrePersist JPA-annotation som gör att metoden som är annoterad med denna körs innan en entitysparas i databasen
//validering metoden kollar att både start och end date finns med och att innan entity sparas
    @PrePersist
    public void validateDates() {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Listing can not be null") Listing getListing() {
        return listing;
    }

    public void setListing(@NotNull(message = "Listing can not be null") Listing listing) {
        this.listing = listing;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

