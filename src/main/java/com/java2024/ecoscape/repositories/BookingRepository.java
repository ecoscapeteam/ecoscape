package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {

}