package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ListingRepository extends JpaRepository<Listing, Long>{

    @Query("SELECT l FROM Listing l " +
            "LEFT JOIN Booking b ON b.listing.id = l.id " +
            "AND ((b.startDate <= :endDate AND b.endDate >= :startDate)) " +
            "LEFT JOIN ListingAvailableDates bd ON bd.listing.id = l.id " +
            "AND ((bd.startDate <= :endDate AND bd.endDate >= :startDate)) " +
            "WHERE b.id IS NULL " +
            "AND bd.id IS NULL")
    Listing findAvailableListings (LocalDate checkInDate, LocalDate checkOutDate, String listingName, int capacity);
}
