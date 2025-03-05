package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.ListingAvailableDatesResponse;
import com.java2024.ecoscape.dto.ListingAvailableDatesRequest;
import com.java2024.ecoscape.models.ListingAvailableDates;
import com.java2024.ecoscape.repositories.ListingAvailableDatesRepository;
import com.java2024.ecoscape.services.ListingAvailableDatesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/availableDates")
public class ListingAvailableDatesController {

    private final ListingAvailableDatesService listingAvailableDatesService;
    private final ListingAvailableDatesRepository listingAvailableDatesRepository;

    public ListingAvailableDatesController(ListingAvailableDatesService listingAvailableDatesService, ListingAvailableDatesRepository listingAvailableDatesRepository) {
        this.listingAvailableDatesService = listingAvailableDatesService;
        this.listingAvailableDatesRepository = listingAvailableDatesRepository;
    }

    @PostMapping("/{listingId}")
    public ResponseEntity<ListingAvailableDatesResponse> createAvailableDates(@Valid @RequestBody ListingAvailableDatesRequest listingAvailableDatesRequest, @PathVariable Long listingId){
        ListingAvailableDatesResponse listingAvailableDatesResponse = listingAvailableDatesService.setAvailableDates(listingId, listingAvailableDatesRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(listingAvailableDatesResponse);
    }

    @GetMapping("/{listingAvailableDatedId}/getSingle")
    public ResponseEntity<ListingAvailableDatesResponse> getSingleAvailableDates(@PathVariable Long listingAvailableDatedId){
        ListingAvailableDatesResponse listingAvailableDatesResponse = listingAvailableDatesService.getSingleAvailableDatesByHost(listingAvailableDatedId);
        return ResponseEntity.ok(listingAvailableDatesResponse);
    }

    @GetMapping("/{listingId}/getAll")
    public ResponseEntity<List<ListingAvailableDatesResponse>> getAllAvailableDatesOfListing(@PathVariable Long listingId){
        List<ListingAvailableDatesResponse> listOfAllAvailableDatesByHostOfListing = listingAvailableDatesService.getAvailableDatesOfAListing(listingId);
        return ResponseEntity.ok(listOfAllAvailableDatesByHostOfListing);
    }

    @PutMapping("/{availableDatesId}")
    public ResponseEntity<ListingAvailableDatesResponse> updateSingleAvailableDatesById(@PathVariable Long availableDatesId,
                                                                                        @RequestParam("startDate") LocalDate startDate,
                                                                                        @RequestParam("endDate") LocalDate endDate){
        ListingAvailableDatesResponse updatedListingAvailableDatesResponse = listingAvailableDatesService.updateSingleAvailableDates(availableDatesId, startDate, endDate);
        return ResponseEntity.ok(updatedListingAvailableDatesResponse);
    }

    @DeleteMapping("/{availableDatesId}/deleteSingle")
    public ResponseEntity<String> deleteSingleAvailableDates(@PathVariable Long availableDatesId) {
        ListingAvailableDates listingAvailableDates = listingAvailableDatesRepository.findById(availableDatesId).orElseThrow(() -> new NoSuchElementException("Available dates by host not found"));
        listingAvailableDatesService.deleteSingleAvailableDatesByHost(availableDatesId);
        return ResponseEntity.ok("The dates from " + listingAvailableDates.getStartDate() + " to " + listingAvailableDates.getEndDate() + " are successfully set as unavailable!");
    }

    @DeleteMapping("/{listingId}/deleteAll")
    public ResponseEntity<String> deleteAllAvailableDatesOfAListing(@PathVariable Long listingId){
        String resultMessage = listingAvailableDatesService.deleteAllAvailableDatesByHostOfAListing(listingId);
        return ResponseEntity.ok(resultMessage);
    }
}
