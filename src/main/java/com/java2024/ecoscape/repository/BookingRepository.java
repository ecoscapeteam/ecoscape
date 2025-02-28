package com.java2024.ecoscape.repository;

import com.java2024.ecoscape.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {


    Optional<Booking> findById(Long bookingId);
}
