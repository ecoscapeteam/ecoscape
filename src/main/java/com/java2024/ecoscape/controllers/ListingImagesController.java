package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.ListingImagesRequest;
import com.java2024.ecoscape.dto.ListingImagesResponse;
import com.java2024.ecoscape.models.ListingImages;
import com.java2024.ecoscape.services.ListingImagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ListingImagesController {
    private final ListingImagesService listingImagesService;

    public ListingImagesController(ListingImagesService listingImagesService) {
        this.listingImagesService = listingImagesService;
    }

    @PostMapping
    public ResponseEntity<?> createListingImage(@RequestBody ListingImagesRequest request) {
        if (listingImagesService.existsByImageUrl(request.getImageUrl())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("That image is already uploaded.");
        }

        ListingImages listingImages = listingImagesService.createListingImages(request);

        ListingImagesResponse listingImagesResponse = new ListingImagesResponse(
                listingImages.getImageUrl(),
                "Image got uploaded successfully!"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(listingImagesResponse);
    }
}
