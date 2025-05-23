package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.Booking;
import com.java2024.ecoscape.models.Status;
import com.java2024.ecoscape.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findById(Long bookingId);

    boolean existsByListingIdAndEndDateAfterAndStatus(Long listingId, LocalDate today, Status status);

    void deleteByListingId(Long listingId);

    List<Booking> findByUser(User user);

}
