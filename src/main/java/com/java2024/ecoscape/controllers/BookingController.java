package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.BookingDTO;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.repository.ListingRepository;
import com.java2024.ecoscape.services.BookingService;
import com.java2024.ecoscape.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
private final UserService userService;
private final ListingRepository listingRepository;

    public BookingController(BookingService bookingService, UserService userService, ListingRepository listingRepository) {
        this.bookingService = bookingService;
this.userService =userService;
this.listingRepository = listingRepository;
    }

@PostMapping
    public ResponseEntity<BookingDTO>createBooking(@RequestBody @Valid BookingDTO bookingDTO,
                                                    @RequestParam Long userId,
                                                    @RequestParam Long listingId) {
    /* Fetch user and listing objects based on IDs
    (Need to implement these methods in service/repository)*/

    User user = userService.findUserById(userId);// Call your UserService or UserRepository to fetch user by userId

            Optional<Listing> listing = listingRepository.findById(listingId);// Call your ListingService or ListingRepository to fetch listing by listingId
    if (user == null || listing.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // if do not found return 404
    }
            // create the booking using the booking service
            BookingDTO createdBooking = bookingService.createBooking(bookingDTO, user , listing.orElse(null));

    return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
}

@GetMapping
    public ResponseEntity<List<BookingDTO>>getAllBooking(){
        List<BookingDTO>bookings = bookingService.getAllbookings(); // call booking service method
        return new ResponseEntity<>(bookings, HttpStatus.OK); // return bookingDTO list with ok
}



}

