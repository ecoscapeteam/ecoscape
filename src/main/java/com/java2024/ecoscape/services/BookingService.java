package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.BookingRequest;
import com.java2024.ecoscape.dto.BookingResponse;
import com.java2024.ecoscape.models.Booking;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.Status;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.repositories.BookingRepository;
import com.java2024.ecoscape.repositories.ListingRepository;
import com.java2024.ecoscape.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final EmailService emailService;
    @Value("${service.fee:0.1}")
    private Double serviceFee;

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final ListingAvailableDatesService listingAvailableDatesService;


    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, ListingRepository listingRepository, ListingAvailableDatesService listingAvailableDatesService,
                          EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.listingAvailableDatesService = listingAvailableDatesService;
        this.emailService = emailService;
    }

    public BookingResponse convertBookingEntityToBookingResponse(Booking booking ) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(booking.getId());
        bookingResponse.setUserId(booking.getUser().getId());
        bookingResponse.setListingId(booking.getListing().getId());
        bookingResponse.setFirstName(booking.getFirstName());
        bookingResponse.setLastName(booking.getLastName());
        bookingResponse.setUsersContactPhoneNumber(booking.getUsersContactPhoneNumber());
        bookingResponse.setUsersContactEmail(booking.getUsersContactEmail());
        bookingResponse.setStartDate(booking.getStartDate());
        bookingResponse.setEndDate(booking.getEndDate());
        bookingResponse.setStatus(booking.getStatus());
        bookingResponse.setGuests(booking.getGuests());
        bookingResponse.setPricePerNight(booking.getListing().getPricePerNight());
        bookingResponse.setCleaningFee(booking.getListing().getCleaningFee());
        long nights = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        BigDecimal serviceFeeInKr = booking.getListing().getPricePerNight()
                .multiply(BigDecimal.valueOf(nights))  // عدد الليالي
                .multiply(BigDecimal.valueOf(serviceFee)) // الخدمة
                .setScale(2, RoundingMode.HALF_UP);
        bookingResponse.setWebsiteFee(serviceFeeInKr);

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
    private BigDecimal calculateTotalPrice(Booking booking, Listing listing) {
        long nights = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        BigDecimal nightsBD = BigDecimal.valueOf(nights);

        BigDecimal pricePerNight = listing.getPricePerNight() != null ? listing.getPricePerNight() : BigDecimal.ZERO;
        BigDecimal cleaningFee = listing.getCleaningFee() != null ? listing.getCleaningFee() : BigDecimal.ZERO;

        BigDecimal serviceFeeBD = pricePerNight.multiply(nightsBD).multiply(BigDecimal.valueOf(serviceFee));

        return pricePerNight.multiply(nightsBD)
                .add(cleaningFee)
                .add(serviceFeeBD)
                .setScale(2, RoundingMode.HALF_UP);
    }
@Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest, Long userId, Long listingId) {
        // Find user and listing
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new NoSuchElementException("Listing not found"));
        // list to collect errors so they all appear at one
        List<String>errors = new ArrayList<>();
        //availability check
        if (!listingAvailableDatesService.checkAvailability(listingId, bookingRequest.getStartDate(), bookingRequest.getEndDate())) {
            errors.add("The listing is unavailable for the requested dates.");
        }

       // control can not have guests more than capacity in listing
        if (bookingRequest.getGuests() > listing.getCapacity()) {
            errors.add("The number of guests exceeds the capacity for this listing.");
        }
        // control can not be end date before start date
        if (bookingRequest.getEndDate().isBefore(bookingRequest.getStartDate())) {
            errors.add("Check-out date cannot be before check-in date.");
        }
        // Validate First Name
        if (bookingRequest.getFirstName() == null || bookingRequest.getFirstName().trim().isEmpty()) {
            errors.add("First name cannot be null or empty.");
        } else if (!bookingRequest.getFirstName().matches("^[a-zA-Z ]+$")) {
            errors.add("First name can only contain letters and spaces.");
        } else if (bookingRequest.getFirstName().length() > 50) {
            errors.add("First name cannot be longer than 50 characters.");
        }

        // Validate Last Name
        if (bookingRequest.getLastName() == null || bookingRequest.getLastName().trim().isEmpty()) {
            errors.add("Last name cannot be null or empty.");
        } else if (!bookingRequest.getLastName().matches("^[a-zA-Z ]+$")) {
            errors.add("Last name can only contain letters and spaces.");
        } else if (bookingRequest.getLastName().length() > 50) {
            errors.add("Last name cannot be longer than 50 characters.");
        }

        // Validate Phone Number
        if (bookingRequest.getUsersContactPhoneNumber() == null || bookingRequest.getUsersContactPhoneNumber().trim().isEmpty()) {
            errors.add("Phone number cannot be null.");
        } else if (!bookingRequest.getUsersContactPhoneNumber().matches("^\\+\\d{1,3}\\d{9}$")) {
            errors.add("That's not a valid phone number.");
        }

        // Validate Email
        if (bookingRequest.getUsersContactEmail() == null || bookingRequest.getUsersContactEmail().trim().isEmpty()) {
            errors.add("Email cannot be null.");
        } else if (!bookingRequest.getUsersContactEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z]{2,})+$")) {
            errors.add("That's not a valid email.");
        } else if (bookingRequest.getUsersContactEmail().length() > 30) {
            errors.add("Email cannot be longer than 30 characters.");
        }

        // Validate Guests
        if (bookingRequest.getGuests() == null) {
            errors.add("Guests cannot be null.");
        } else if (bookingRequest.getGuests() < 1) {
            errors.add("Guests must be at least 1.");
        } else if (bookingRequest.getGuests() > 10) {
            errors.add("Guests cannot be more than 10.");
        }

        // If there are errors, throw an exception with all the error messages
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }



        Booking booking = convertBookingRequestToBookingEntity(bookingRequest, listing);
        booking.setUser(user);
        booking.setListing(listing);
        booking.setStatus(CONFIRMED);
        booking.setFirstName(bookingRequest.getFirstName());
        booking.setLastName(bookingRequest.getLastName());

        // blokera available dates i samband med booking
        listingAvailableDatesService.blockAvailableDatesAfterBooking(listingId, booking);
        // save booking to db
        Booking savedBooking = bookingRepository.save(booking);

        return convertBookingEntityToBookingResponse(savedBooking);
    }


    // method to get all booking
    public List<BookingRequest> getAllbookings() {
        List<Booking> bookings = bookingRepository.findAll(); // Fetch all bookings from the repository
        return bookings.stream()
                .map(this::convertBookingEntityToBookingRequest) // Convert each Booking entity to BookingRequest DTO
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        // تحويل Booking إلى BookingResponse
        return convertBookingEntityToBookingResponse(booking);
    }



    public BookingResponse cancelBookingByUser(Long bookingId){

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // التحقق مما إذا كان الحجز قد تم إلغاؤه مسبقًا
        if (booking.getStatus() == Status.CANCELLED_BY_USER || booking.getStatus() == Status.CANCELLED_BY_HOST) {
            throw new RuntimeException("This booking has already been cancelled.");
        }

       booking.setStatus(CANCELLED_BY_USER);
        listingAvailableDatesService.unblockAvailableDatesAfterCancellation(booking.getListing().getId(), booking);
       bookingRepository.save(booking);
        // إرسال تأكيد الإلغاء بالبريد الإلكتروني
        sendCancellationEmail(booking);
        // تحويل الكيان إلى استجابة
        BookingResponse bookingResponse = convertBookingEntityToBookingResponse(booking);

        // إضافة الرسالة إلى الاستجابة
        bookingResponse.setMessage("The booking number " + booking.getId() + " has been cancelled. A confirmation email has been sent.");

        return bookingResponse;
    }

    public BookingResponse cancelBookingByHost(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        // التحقق مما إذا كان الحجز قد تم إلغاؤه مسبقًا
        if (booking.getStatus() == Status.CANCELLED_BY_USER || booking.getStatus() == Status.CANCELLED_BY_HOST) {
            throw new RuntimeException("This booking has already been cancelled.");
        }


        booking.setStatus(CANCELLED_BY_HOST);
        listingAvailableDatesService.unblockAvailableDatesAfterCancellation(booking.getListing().getId(), booking);
        bookingRepository.save(booking);
        // إرسال تأكيد الإلغاء بالبريد الإلكتروني
        sendCancellationEmail(booking);

        // تحويل الكيان إلى استجابة
        BookingResponse bookingResponse = convertBookingEntityToBookingResponse(booking);

        // إضافة الرسالة إلى الاستجابة
       bookingResponse.setMessage("The booking number " + booking.getId() + " has been cancelled. A confirmation email has been sent.");

        return bookingResponse;
    }

    private void sendCancellationEmail(Booking booking  ) {
        String to = booking.getUsersContactEmail();
        String subject = "Confirm cancellation of booking";
        String text = "Hello!\n" + booking.getFirstName() + ",\n\n" +
                "We would like to inform you that the booking you made with us has been cancelled.\n"
                + "We apologize for any inconvenience this may cause.\n\n"
                + "If you need any assistance, please don't hesitate to contact us.\n\n"
                + "Best regards,\nThe Ecoscape Team.";

        // Sending the email using the EmailService
        emailService.sendEmail(to, subject, text);
    }


    private BookingRequest convertBookingEntityToBookingRequest(Booking booking) {
        BookingRequest bookingRequest = new BookingRequest();

        bookingRequest.setFirstName(booking.getFirstName());
        bookingRequest.setLastName(booking.getLastName());

        bookingRequest.setUsersContactEmail(booking.getUsersContactEmail());
        bookingRequest.setUsersContactPhoneNumber(booking.getUsersContactPhoneNumber());
        bookingRequest.setStartDate(booking.getStartDate());
        bookingRequest.setEndDate(booking.getEndDate());
        bookingRequest.setGuests(booking.getGuests());
        return bookingRequest;
    }
@Transactional
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

