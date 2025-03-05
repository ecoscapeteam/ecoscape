package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    boolean existsByUserId(Long userId);
}
