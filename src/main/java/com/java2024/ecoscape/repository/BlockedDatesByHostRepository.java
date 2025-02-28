package com.java2024.ecoscape.repository;

import com.java2024.ecoscape.models.BlockedDatesByHost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedDatesByHostRepository extends JpaRepository<BlockedDatesByHost, Long> {

    void deleteAllByListingId(Long listingId);

    boolean existsByListingId(Long listingId);

}
