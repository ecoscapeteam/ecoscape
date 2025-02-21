package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.ListingRequest;
import com.java2024.ecoscape.dto.ListingResponse;
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
    public ResponseEntity<ListingResponse> createListing (@Valid @RequestBody ListingRequest listingRequest, @PathVariable Long userId) {
        ListingResponse savedListing = listingService.createListing(userId, listingRequest.getListing(),
                listingRequest.getRules());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedListing);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        Listing listing = listingService.findListingById(id);
        return ResponseEntity.ok(listing);
    }

    @PatchMapping("/{listingId}")
    public ResponseEntity<Listing> partialUpdateListing(@PathVariable Long listingId, @RequestBody Listing newListingDetails){
        Listing updatedListing = listingService.partialUpdateListingById(listingId, newListingDetails);
        return ResponseEntity.ok(updatedListing);
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Listing> deleteListingById(@PathVariable Long listingId) {
        listingService.deleteListingById(listingId);
        return ResponseEntity.noContent().build();
    }




}
