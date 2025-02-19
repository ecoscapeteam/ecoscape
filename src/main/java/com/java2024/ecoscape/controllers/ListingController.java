package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.CreateListingRequest;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.services.ListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Listing> createPlant (@Valid @RequestBody CreateListingRequest createListingRequest, @PathVariable Long userId) {
        Listing savedListing = listingService.createListing(userId, createListingRequest.getListing(),
                createListingRequest.getRules());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedListing);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        Listing listing = listingService.findListingById(id);
        return ResponseEntity.ok(listing);
    }
}
