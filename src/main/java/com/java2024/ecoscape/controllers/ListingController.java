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
    public ResponseEntity<ListingResponse> createListing(@Valid @RequestBody ListingRequest listingRequest, @PathVariable Long userId) {
        ListingResponse listingResponse = listingService.createListing(userId, listingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(listingResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ListingResponse> getListingById(@PathVariable Long id) {
        ListingResponse listingResponse = listingService.getListingById(id);
        return ResponseEntity.ok(listingResponse);
    }

    @PatchMapping("/{listingId}")
    public ResponseEntity<ListingResponse> partialUpdateListing(@PathVariable Long listingId, @RequestBody ListingRequest listingRequest) {
        ListingResponse listingResponse = listingService.partialUpdateListingById(listingId, listingRequest);
        return ResponseEntity.ok(listingResponse);
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Listing> deleteListingById(@PathVariable Long listingId) {
        listingService.deleteListingById(listingId);
        return ResponseEntity.noContent().build();

    }
}
