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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.java2024.ecoscape.models.Status.*;

@Service
public class BookingService {

    @Value("${service.fees}")
    private Double serviceFee;

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, ListingRepository listingRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    public BookingResponse convertBookingEntityToBookingResponse(Booking booking ) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(booking.getId());
        bookingResponse.setUserId(booking.getUser().getId());
        bookingResponse.setListingId(booking.getListing().getId());
        bookingResponse.setFirstName(booking.getUser().getFirstName());
        bookingResponse.setLastName(booking.getUser().getLastName());
        bookingResponse.setUsersContactPhoneNumber(booking.getUser().getContactPhoneNumber());
        bookingResponse.setUsersContactEmail(booking.getUser().getContactEmail());
        bookingResponse.setStartDate(booking.getStartDate());
        bookingResponse.setEndDate(booking.getEndDate());
        bookingResponse.setStatus(booking.getStatus());
        bookingResponse.setGuests(booking.getGuests());
        bookingResponse.setPricePerNight(booking.getListing().getPricePerNight());
        bookingResponse.setCleaningFee(booking.getListing().getCleaningFee());
        bookingResponse.setWebsite_Fee(BigDecimal.valueOf(serviceFee));
        bookingResponse.setTotalPrice(booking.getTotalPrice());


        return bookingResponse;
    }
    public Booking convertBookingRequestToBookingEntity(BookingRequest bookingRequest, Listing listing) {
        Booking booking = new Booking();
        booking.setFirstName(bookingRequest.getFirstName());
        booking.setLastName(bookingRequest.getLastName());
        booking.setUsersContactEmail(bookingRequest.getUsersContactEmail());
        booking.setUsersContactPhoneNumber(bookingRequest.getUsersContactPhoneNumber());
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setGuests(bookingRequest.getGuests());

        // ensure the listing do not empty
        if (listing == null) {
            throw new IllegalArgumentException("Listing cannot be null when creating a booking.");
        }

        // تعيين القائمة للحجز
        booking.setListing(listing);

        // calculate Total price
        booking.setTotalPrice(calculateTotalPrice(booking, listing));



        return booking;
    }
    //Method to calculate Total price
    private BigDecimal calculateTotalPrice(Booking booking, Listing listing) {
        long nights = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        BigDecimal nightsBD = BigDecimal.valueOf(nights);
        BigDecimal pricePerNight = listing.getPricePerNight();
        BigDecimal cleaningFee = listing.getCleaningFee();
        BigDecimal serviceFeeBD = pricePerNight.multiply(BigDecimal.valueOf(0.1)); // 10% رسوم خدمة

        return pricePerNight.multiply(nightsBD)
                .add(cleaningFee)
                .add(serviceFeeBD)
                .setScale(2, RoundingMode.HALF_UP);
    }



    public BookingResponse createBooking(BookingRequest bookingRequest, Long userId, Long listingId) {
        // Find user and listing
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new NoSuchElementException("Listing not found"));
        // list to collect errors so they all appear at one
        List<String>errors = new ArrayList<>();

        // control can not have guests more than capacity in listing
        if (bookingRequest.getGuests() > listing.getCapacity()) {
            errors.add("The number of guests exceeds the capacity for this listing.");
        }

        // control can not be end date before start date
        if (bookingRequest.getEndDate().isBefore(bookingRequest.getStartDate())) {
            errors.add("Check-out date cannot be before check-in date.");
        }

        // if have two errors together, sen exception with what is wrong
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }

        Booking booking = convertBookingRequestToBookingEntity(bookingRequest, listing);
        booking.setUser(user);
        booking.setListing(listing);
        booking.setStatus(CONFIRMED);
        booking.setFirstName(user.getFirstName());
        booking.setLastName(user.getLastName());

        Booking savedBooking = bookingRepository.save(booking);
        return convertBookingEntityToBookingResponse(savedBooking);
    }


    public List<BookingRequest> getAllbookings() {
        List<Booking> bookings = bookingRepository.findAll(); // Fetch all bookings from the repository
        return bookings.stream()
                .map(this::convertBookingEntityToBookingRequest) // Convert each Booking entity to BookingRequest DTO
                .collect(Collectors.toList());
    }

    private BookingRequest convertBookingEntityToBookingRequest(Booking booking) {
        BookingRequest bookingRequest = new BookingRequest();

        bookingRequest.setFirstName(booking.getUser().getFirstName());
        bookingRequest.setLastName(booking.getUser().getLastName());

        bookingRequest.setUsersContactEmail(booking.getUser().getContactEmail());
        bookingRequest.setUsersContactPhoneNumber(booking.getUser().getContactPhoneNumber());
        bookingRequest.setStartDate(booking.getStartDate());
        bookingRequest.setEndDate(booking.getEndDate());
        bookingRequest.setGuests(booking.getGuests());
        return bookingRequest;
    }

    public BookingResponse updateBooking(BookingRequest bookingRequest, Long bookingId, Listing listing, User user) {
        // Find Booking
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NoSuchElementException("Booking not found"));


        // list to collect errors so they all appeared at one
        List<String>errors = new ArrayList<>();

        // control can not have guests more than capacity in listing
        if (bookingRequest.getGuests() > listing.getCapacity()) {
            errors.add("The number of guests exceeds the capacity for this listing.");

        }

        // control can not be end date before start date
        if (bookingRequest.getEndDate().isBefore(bookingRequest.getStartDate())) {
            errors.add("Check-out date cannot be before check-in date.");
        }

        // if have two errors together, sen exception with what is wrong
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));

        }



        try {
            // Försök att uppdatera första namnet
            existingBooking.setFirstName(bookingRequest.getFirstName());
            System.out.println("First name updated to: " + existingBooking.getFirstName());
            bookingRepository.save(existingBooking);
        } catch (Exception e) {
            System.out.println("Error updating first name: " + e.getMessage());
            throw e; // Rulla tillbaka om något går fel
        }


        if (bookingRequest.getLastName() != null) {
            existingBooking.setLastName(bookingRequest.getLastName());
        }

        if (bookingRequest.getUsersContactPhoneNumber() != null) {
            existingBooking.setUsersContactPhoneNumber(bookingRequest.getUsersContactPhoneNumber());
        }

        if (bookingRequest.getUsersContactEmail() != null) {
            existingBooking.setUsersContactEmail(bookingRequest.getUsersContactEmail());
        }

        if (bookingRequest.getStartDate() != null) {
            existingBooking.setStartDate(bookingRequest.getStartDate());
        }

        if (bookingRequest.getEndDate() != null) {
            existingBooking.setEndDate(bookingRequest.getEndDate());
        }

        if (bookingRequest.getStatus() != null) {
            existingBooking.setStatus(bookingRequest.getStatus());
        }

        if (bookingRequest.getGuests() != null) {
            existingBooking.setGuests(bookingRequest.getGuests());
        }




        Booking udatedBooking = bookingRepository.save(existingBooking);
        return convertBookingEntityToBookingResponse(udatedBooking);



    }


    public ResponseEntity<String> deleteBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(booking -> {
                    bookingRepository.delete(booking);
                    return ResponseEntity.ok("Booking deleted successfully"); // Return success message with HTTP 200 status
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found")); // Return error message with HTTP 404 status
    }


}