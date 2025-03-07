package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.ListingAvailableDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ListingAvailableDatesRepository extends JpaRepository<ListingAvailableDates, Long> {


    void deleteAllByListingId(Long listingId);

    boolean existsByListingId(Long listingId);

    List<ListingAvailableDates> findAllByListingId (Long listing);

    boolean existsByListingIdAndStartDateBeforeAndEndDateAfter(Long listingId, LocalDate checkInDate, LocalDate checkOutDate);

    public Optional<ListingAvailableDates> findByListingIdAndStartDateAndEndDate(Long listingId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(l) > 0 FROM ListingAvailableDates l WHERE l.listing.id = :listingId AND " +
            "((l.startDate <= :endDate AND l.endDate >= :startDate))")
    boolean existsByListingIdAndOverlappingDates(@Param("listingId") Long listingId,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);



}
