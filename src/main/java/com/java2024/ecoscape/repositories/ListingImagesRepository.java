package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.ListingImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListingImagesRepository extends JpaRepository<ListingImages, Long> {
    Optional<ListingImages> findByImageUrl(String imageUrl);

    List<ListingImages> findByListingId(Long listingId);

    long countByListing(Listing listing);
}
