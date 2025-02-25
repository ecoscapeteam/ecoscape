package com.java2024.ecoscape.repository;

import com.java2024.ecoscape.models.ListingImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListingImagesRepository extends JpaRepository<ListingImages, Long> {
    Optional<ListingImages> findByImageUrl(String imageUrl);

}
