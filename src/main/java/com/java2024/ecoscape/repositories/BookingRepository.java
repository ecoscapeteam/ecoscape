package com.java2024.ecoscape.repository;

import com.java2024.ecoscape.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {

}
