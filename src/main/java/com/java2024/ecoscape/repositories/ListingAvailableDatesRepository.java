package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.ListingAvailableDates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ListingAvailableDatesRepository extends JpaRepository<ListingAvailableDates, Long> {


    void deleteAllByListingId(Long listingId);

    boolean existsByListingId(Long listingId);

    List<ListingAvailableDates> findAllByListingId (Long listing);

    boolean existsByListingIdAndStartDateBeforeAndEndDateAfter(Long listingId, LocalDate checkInDate, LocalDate checkOutDate);


}
