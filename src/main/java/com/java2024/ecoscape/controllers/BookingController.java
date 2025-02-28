package com.java2024.ecoscape.controllers;
import com.java2024.ecoscape.dto.BookingRequest;
import com.java2024.ecoscape.dto.BookingResponse;
import com.java2024.ecoscape.models.Booking;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.repository.BookingRepository;
import com.java2024.ecoscape.repository.ListingRepository;
import com.java2024.ecoscape.repository.UserRepository;
import com.java2024.ecoscape.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

import static com.java2024.ecoscape.models.Status.CANCELLED_BY_USER;
import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    public BookingController(BookingService bookingService, BookingRepository bookingRepository,
                             UserRepository userRepository,
                             ListingRepository listingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody @Valid BookingRequest bookingRequest,
            @RequestParam Long userId,
            @RequestParam Long listingId) {
        BookingResponse createdBooking = bookingService.createBooking(bookingRequest, userId, listingId);
        return ResponseEntity.status(CREATED).body(createdBooking);
    }

    @GetMapping
    public ResponseEntity<List<BookingRequest>> getAllBooking() {
        List<BookingRequest> bookings = bookingService.getAllbookings(); // call booking service method
        return new ResponseEntity<>(bookings, HttpStatus.OK); // return bookingDTO list with ok
    }


    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long bookingId,
                                         @RequestBody BookingRequest bookingRequest) {

        // Retrieve the user from the database or session (assuming userId is in the request)

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Retrieve the listing from the database (assuming it is needed for validation)

        Listing listing = listingRepository.findById(bookingRequest.getListingId())
                .orElseThrow(() -> new NoSuchElementException("Listing not found"));



        // Call the service to update the booking
        BookingResponse updatedBooking = bookingService.updateBooking(bookingRequest, bookingId, listing, user);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBooking);

    }

}





