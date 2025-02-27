package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.ListingImagesGetResponse;
import com.java2024.ecoscape.dto.ListingImagesRequest;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.ListingImages;
import com.java2024.ecoscape.repository.ListingImagesRepository;
import com.java2024.ecoscape.repository.ListingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class ListingImagesService {
    private final ListingRepository listingRepository;
    private final ListingImagesRepository listingImagesRepository;


    public ListingImagesService(ListingRepository listingRepository, ListingImagesRepository listingImagesRepository) {
        this.listingRepository = listingRepository;
        this.listingImagesRepository = listingImagesRepository;
    }

    public boolean existsByImageUrl(String imageUrl) {
        return listingImagesRepository.findByImageUrl(imageUrl).isPresent();
    }

    public ListingImages createListingImages(ListingImagesRequest listingImagesRequest) {
        Listing listing = listingRepository.findById(listingImagesRequest.getListing().getId())
                .orElseThrow(() -> new NoSuchElementException("Listing was not found."));

        ListingImages listingImages = new ListingImages();
        listingImages.setListing(listing);
        listingImages.setImageUrl(listingImagesRequest.getImageUrl());

        return listingImagesRepository.save(listingImages);
    }

    public ListingImages findImageById(Long id) {
        return listingImagesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Listing image not found"));
    }

    public List<ListingImagesGetResponse> findAllListingImages() {
        List<ListingImages> listingImages = listingImagesRepository.findAll();

        return listingImages.stream()
                .map(images -> new ListingImagesGetResponse(
                        images.getListing().getId(),
                        images.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    public void deleteListingImage(Long id) {
        ListingImages existingImage = listingImagesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Listing image not found"));

        listingImagesRepository.deleteById(id);
    }
}
