package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.BookingRequest;
import com.java2024.ecoscape.dto.BookingResponse;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.repositories.BookingRepository;
import com.java2024.ecoscape.repositories.ListingRepository;
import com.java2024.ecoscape.repositories.UserRepository;
import com.java2024.ecoscape.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


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
    public ResponseEntity<?> createBooking(
            @RequestBody @Valid BookingRequest bookingRequest,
            @RequestParam Long listingId,
            BindingResult bindingResult) {

        // إذا كانت هناك أخطاء في التحقق من الصحة
        // If there are validation errors
        if (bindingResult.hasErrors()) {
            // جمع الأخطاء collect errors
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            // إرجاع رسالة الأخطاء كاستجابة
            // Return error response
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // إذا لم تكن هناك أخطاء، قم بإنشاء الحجز
        // If no errors, create the booking
        BookingResponse createdBooking = bookingService.createBooking(bookingRequest, listingId);

        // إرجاع الحجز الذي تم إنشاؤه
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }


    @GetMapping
    public ResponseEntity<List<BookingRequest>> getAllBooking() {
        List<BookingRequest> bookings = bookingService.getAllbookings(); // call booking service method
        return new ResponseEntity<>(bookings, HttpStatus.OK); // return bookingDTO list with ok
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        BookingResponse booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }



@PatchMapping("/{id}/cancel/user")
public ResponseEntity<?> cancelByUser(@PathVariable Long id) {
    try {
        // Calling the service method to cancel the booking by the user
        BookingResponse response = bookingService.cancelBookingByUser(id);
        // Returning the updated booking response
        return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage()); // Returning 404 if booking not found
    }
}

@PatchMapping("/{id}/cancel/host")
public ResponseEntity<?> cancelByHost(@PathVariable Long id) {
    try {
        // Calling the service method to cancel the booking by the user
        BookingResponse response = bookingService.cancelBookingByHost(id);
        // Returning the updated booking response
        return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage()); // Returning 404 if booking not found
    }
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



    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(booking -> {
                    bookingRepository.delete(booking);
                    return ResponseEntity.ok("Booking deleted successfully"); // Response with 200 OK
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found")); // Response with 404 if booking is not found
    }

}
