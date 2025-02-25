package com.java2024.ecoscape.services;


import com.java2024.ecoscape.dto.BookingDTO;
import com.java2024.ecoscape.models.Booking;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.repository.BookingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // use to business logic or affärslogik

public class BookingService {
    public BookingService (BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    private final BookingRepository bookingRepository; // // Repository for booking

    public BookingDTO convertToDTO(Booking booking) { // This method takes a Booking object as input and converts it into a BookingDTO

        BookingDTO dto = new BookingDTO(); // creates a new instance of BookingDTO (dto) to store the converted data

        dto.setUserId(booking.getUser().getId()); // Retrieves the user object from the booking, gets its ID, and sets it in dto
        dto.setListingId(booking.getListing().getId());
        dto.setFirstName(booking.getFirstName()); // Copies the firstName from booking to dto
        dto.setLastName(booking.getLastName());
        dto.setEmail(booking.getEmail());
        dto.setPhoneNumber(booking.getPhoneNumber());
        dto.setStartDate(booking.getStartDate());
        dto.setEndDate(booking.getEndDate());
        dto.setStatus(booking.getStatus());
        dto.setGuests(booking.getGuests());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setWebsitesFee(booking.getWebsitesFee());
        return dto;  // returns the populated BookingDTO object
    }

    /* mothod to convert the BookingDTO to Booking model
    BookingDTO only contains userId and listingId, not the full objects.
When converting back to Booking, need the actual User and Listing objects,
which man can fetch from the database before calling this method.
     */
public Booking convertTOMODEL(BookingDTO dto, User user, Listing listing) {
    Booking booking = new Booking(); // create a ew Booking object

    booking.setUser(user); // Set the user object from parameter
    booking.setListing(listing); // Set the listing object from parameter
    booking.setFirstName(dto.getFirstName()); // Set the first name from DTO to model
    booking.setLastName(dto.getLastName());
    booking.setEmail(dto.getEmail());
    booking.setPhoneNumber(dto.getPhoneNumber());
    booking.setStartDate(dto.getStartDate());
    booking.setEndDate(dto.getEndDate());
    booking.setStatus(dto.getStatus());
    booking.setGuests(dto.getGuests());
    booking.setTotalPrice(dto.getTotalPrice());
    booking.setWebsitesFee(dto.getWebsitesFee());

    return booking;
}

// method to create a booking
    public BookingDTO createBooking (@Valid BookingDTO dto , User user, Listing listing) {

        // convert BookingDTO to model
        Booking booking = convertTOMODEL(dto, user, listing);

        // save the booking to database
        Booking savedBooking = bookingRepository.save(booking);

        // Convert the save Booking back to BookingDTO and return it
        return convertToDTO(savedBooking);
    }

    // Method to get all booking
    public List<BookingDTO> getAllbookings(){
    List<Booking> bookings = bookingRepository.findAll(); // fetch from db

        return bookings.stream()//convert from Booking to BookingDTO
                .map(this::convertToDTO) // call convert method
                .collect(Collectors.toList());

    }



}
