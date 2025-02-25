package com.java2024.ecoscape.services;


import com.java2024.ecoscape.dto.BookingRequest;
import com.java2024.ecoscape.dto.BookingResponse;
import com.java2024.ecoscape.models.Booking;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.repository.BookingRepository;
import com.java2024.ecoscape.repository.ListingRepository;
import com.java2024.ecoscape.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service // use to business logic or affärslogik
public class BookingService {

    @Value("${service.fees})
    private Double  serviceFee;

    private final BookingRepository bookingRepository; // // Repository for booking
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;


    public BookingService (BookingRepository bookingRepository, UserRepository userRepository,
                           ListingRepository listingRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    public BookingResponse convertBookingEntityToBookingResponse(Booking booking) { // This method takes a Booking object as input and converts it into a BookingDTO
        BookingResponse bookingResponse = new BookingResponse(); // creates a new instance of BookingDTO (dto) to store the converted data
        bookingResponse.setBookingId(booking.getId());
        bookingResponse.setUserId(booking.getUser().getId());
        bookingResponse.setListingId(booking.getListing().getId());
        bookingResponse.setFirstName(booking.getFirstName());
        bookingResponse.setLastName(booking.getLastName());
        bookingResponse.setUsersContactPhoneNumber(booking.getUser().getContactPhoneNumber());
        bookingResponse.setUsersContactEmail(booking.getUser().getContactEmail());
        bookingResponse.setStartDate(booking.getStartDate());
        bookingResponse.setEndDate(booking.getEndDate());
        bookingResponse.setStatus(booking.getStatus());
        bookingResponse.setGuests(booking.getGuests());
        bookingResponse.setCleaningFee(booking.getListing().getCleaningFee());
        bookingResponse.setWebsite_Fee(booking.getWebsite_Fee());
        bookingResponse.setTotalPrice(booking.getTotalPrice());


        return bookingResponse;  // returns the populated BookingDTO object
    }

  /* mothod to convert the BookingDTO to Booking model
    BookingDTO only contains userId and listingId, not the full objects.
When converting back to Booking, need the actual User and Listing objects,
which man can fetch from the database before calling this method.*/

public Booking convertBookingResponseToBookingEntity(BookingRequest bookingRequest, User user, Listing listing) {
    Booking booking = new Booking(); // create a ew Booking object
    booking.setUser(user); // Set the user object from parameter
    booking.setListing(listing); // Set the listing object from parameter
    booking.setFirstName(user.getFirstName()); // Set the first name from DTO to model
    booking.setLastName(user.getLastName());
    booking.setUsersContactEmail(bookingRequest.getUsersContactEmail());
    booking.getUsersContactPhoneNumber(bookingRequest.getUsersContactPhoneNumber());
    booking.setStartDate(bookingRequest.getStartDate());
    booking.setEndDate(bookingRequest.getEndDate());
    booking.setGuests(bookingRequest.getGuests());

    return booking;
}

// method to create a booking
    public BookingRequest createBooking (@Valid BookingRequest dto , Long userId, Long listingId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        // convert BookingDTO to model
        Booking booking = convertBookingResponseToBookingEntity(dto, user, listing);

        // save the booking to database
        Booking savedBooking = bookingRepository.save(booking);

        // Convert the save Booking back to BookingDTO and return it
        return convertToDTO(savedBooking);
    }

    // Method to get all booking
    public List<BookingRequest> getAllbookings(){
    List<Booking> bookings = bookingRepository.findAll(); // fetch from db

        return bookings.stream()//convert from Booking to BookingDTO
                .map(this::convertToDTO) // call convert method
                .collect(Collectors.toList());

    }





