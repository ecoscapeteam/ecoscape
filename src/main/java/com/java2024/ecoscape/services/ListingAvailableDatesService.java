package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.ListingAvailableDatesRequest;
import com.java2024.ecoscape.dto.ListingAvailableDatesResponse;
import com.java2024.ecoscape.models.Booking;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.ListingAvailableDates;
import com.java2024.ecoscape.repositories.ListingAvailableDatesRepository;
import com.java2024.ecoscape.repositories.ListingRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ListingAvailableDatesService {
    private final ListingAvailableDatesRepository listingAvailableDatesRepository;
    private final ListingRepository listingRepository;

    public ListingAvailableDatesService(ListingAvailableDatesRepository listingAvailableDatesRepository, ListingRepository listingRepository) {
        this.listingAvailableDatesRepository = listingAvailableDatesRepository;
        this.listingRepository = listingRepository;
    }



    @Transactional
    public ListingAvailableDatesResponse setAvailableDates(Long listingId, ListingAvailableDatesRequest listingAvailableDatesRequest){
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));

        LocalDate oneYearFromNow = LocalDate.now().plusYears(1);
        ListingAvailableDates listingAvailableDates = convertListingAvailableDatesRequestToAvailableDatesEntity(listingAvailableDatesRequest);
        LocalDate endDate = listingAvailableDates.getEndDate();
    //felhantering av eventuella overlap
        Optional<ListingAvailableDates> existingDateRange = listingAvailableDatesRepository
                .findByListingIdAndStartDateAndEndDate(listingId, listingAvailableDates.getStartDate(), listingAvailableDates.getEndDate());


        if (existingDateRange.isPresent()) {
            throw new DataIntegrityViolationException("The available date range already exists.");
    }
        boolean isOverlapping = listingAvailableDatesRepository.existsByListingIdAndOverlappingDates(
                listingId, listingAvailableDates.getStartDate(), listingAvailableDates.getEndDate());

        if(isOverlapping){
            throw new DataIntegrityViolationException("The available date range overlaps with an existing range.");
        }
//felhantering av eventuella försök av hosten att lägga availability for mer än 1 år framåt
        if (endDate.isAfter(oneYearFromNow)) {
            throw new IllegalArgumentException("The availability cannot be more than one year ahead .");
        }

        listingAvailableDates.setListing(listing);


        ListingAvailableDates savedListingAvailableDates = listingAvailableDatesRepository.save(listingAvailableDates);
        return convertListingAvailableDatesEntityToListingAvailableDatesResponse(savedListingAvailableDates);
    }

    public ListingAvailableDatesResponse getSingleAvailableDatesByHost(Long listingAvailableDatesId){
        ListingAvailableDates listingAvailableDates = listingAvailableDatesRepository.findById(listingAvailableDatesId).orElseThrow(() -> new NoSuchElementException("No such available dates "));
        return convertListingAvailableDatesEntityToListingAvailableDatesResponse(listingAvailableDates);
    }

    public List<ListingAvailableDatesResponse> getAvailableDatesOfAListing(Long listingId){
        listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        List <ListingAvailableDates> listingAvailableDatesList = listingAvailableDatesRepository.findAllByListingId(listingId);
        return convertListingAvailableDatesEntityToListingAvailableDatesResponse(listingAvailableDatesList);
    }

    public ListingAvailableDatesResponse updateSingleAvailableDates(Long listingAvailableDatesId, LocalDate newStartDate, LocalDate newEndDate){
        ListingAvailableDates listingAvailableDates = listingAvailableDatesRepository
                .findById(listingAvailableDatesId).orElseThrow(() -> new NoSuchElementException("No such available dates "));
        listingAvailableDates.setStartDate(newStartDate);
        listingAvailableDates.setEndDate(newEndDate);
        //felhantering av eventuella overlap, kallar på native JPA metoden findListing för att hitta listingId efter singleAvailableDateId
        Long listingId = listingAvailableDatesRepository.findListingIdByAvailableDatesId(listingAvailableDatesId);
        Optional<ListingAvailableDates> existingDateRange = listingAvailableDatesRepository
                .findByListingIdAndStartDateAndEndDate(listingId, listingAvailableDates.getStartDate(), listingAvailableDates.getEndDate());

        //kollar att inte available dates existerar, dvs behöver inte updateras om det är samma
        if (existingDateRange.isPresent()) {
            throw new DataIntegrityViolationException("The available date range already exists.");
        }
        boolean isOverlapping = listingAvailableDatesRepository.existsByListingIdAndOverlappingDates(
                listingId, listingAvailableDates.getStartDate(), listingAvailableDates.getEndDate());

//kolla att det inte finns överlapp och att inte det är samma available dates som vi vill ändra på
        ListingAvailableDates overlappingDates = listingAvailableDatesRepository.findOverlappingDateRange(listingId, newStartDate, newEndDate);
        if (isOverlapping && !overlappingDates.getId().equals(listingAvailableDatesId)) {
            throw new DataIntegrityViolationException("The available date range overlaps with an existing range.");
        }

//felhantering av eventuella försök av hosten att lägga availability for mer än 1 år framåt
        LocalDate oneYearFromNow = LocalDate.now().plusYears(1);
        if (newEndDate.isAfter(oneYearFromNow)) {
            throw new IllegalArgumentException("The availability cannot be more than one year ahead .");
        }
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        listingAvailableDates.setListing(listing);
        ListingAvailableDates updateListingAvailableDates = listingAvailableDatesRepository.save(listingAvailableDates);
        return convertListingAvailableDatesEntityToListingAvailableDatesResponse(updateListingAvailableDates);
    }

    public void deleteSingleAvailableDatesByHost(Long availableDatesId){
        ListingAvailableDates listingAvailableDates = listingAvailableDatesRepository.findById(availableDatesId).orElseThrow(() -> new NoSuchElementException("No such available dates "));
        listingAvailableDatesRepository.delete(listingAvailableDates);
    }

    //behöver Transactional annotation för metoden ska funka, funkade inte utan den
    @Transactional
    public String deleteAllAvailableDatesByHostOfAListing(Long listingId) {
        listingRepository.findById(listingId)
                .orElseThrow(() -> new NoSuchElementException("Listing not found"));

        if (listingAvailableDatesRepository.existsByListingId(listingId)) {
            listingAvailableDatesRepository.deleteAllByListingId(listingId);
            return "All available dates for listing with ID " + listingId + " have been successfully removed.";
        } else {
            return "No available dates found for listing ID " + listingId;
        }
    }

    public boolean checkAvailability(Long listingId, LocalDate startDate, LocalDate endDate) {
        listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        boolean isAvailable = listingAvailableDatesRepository.existsByListingIdAndStartDateBeforeAndEndDateAfter(
                listingId, endDate, startDate);
        return isAvailable;
    }


    @Transactional
    public void blockAvailableDatesAfterBooking(Long listingId, Booking newBooking) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        List<ListingAvailableDates> availableDatesRangesOfListing = listingAvailableDatesRepository.findAllByListingId(listingId);

        if (availableDatesRangesOfListing.isEmpty()) {
            throw new NoSuchElementException("No available dates found for the given listing.");
        }

        LocalDate bookingStartDate = newBooking.getStartDate();
        LocalDate bookingEndDate = newBooking.getEndDate();

        for (ListingAvailableDates availableDates : availableDatesRangesOfListing) {
            if (bookingStartDate.isBefore(availableDates.getEndDate()) && bookingEndDate.isAfter(availableDates.getStartDate())) {

                //om bokningen har samma startdatum som availableDateRange
                if (bookingStartDate.isEqual(availableDates.getStartDate()) && bookingEndDate.isEqual(availableDates.getEndDate())) {
                    listingAvailableDatesRepository.delete(availableDates);
                }

                //om bokningen är i mitten på range, och separerar range, två resterande range sparas, det gamla range tas bort
                else if (bookingStartDate.isAfter(availableDates.getStartDate()) && bookingEndDate.isBefore(availableDates.getEndDate())) {
                    ListingAvailableDates rangeBeforeNewBooking = new ListingAvailableDates();
                    rangeBeforeNewBooking.setListing(listing);
                    rangeBeforeNewBooking.setStartDate(availableDates.getStartDate());
                    rangeBeforeNewBooking.setEndDate(bookingStartDate.minusDays(0));


                    ListingAvailableDates rangeAfterBooking = new ListingAvailableDates();
                    rangeAfterBooking.setListing(listing);
                    rangeAfterBooking.setStartDate(bookingEndDate.plusDays(0));
                    rangeAfterBooking.setEndDate(availableDates.getEndDate());

                    List<ListingAvailableDates> rangesToSave = new ArrayList<>();
                    rangesToSave.add(rangeBeforeNewBooking);
                    rangesToSave.add(rangeAfterBooking);

                    listingAvailableDatesRepository.delete(availableDates);
                    listingAvailableDatesRepository.saveAll(rangesToSave);


                }
                /////om bokningen är i början på range
                else if (bookingStartDate.isEqual(availableDates.getStartDate()) && bookingEndDate.isBefore(availableDates.getEndDate())) {
                    ListingAvailableDates rangeRemainingAfter = new ListingAvailableDates();
                    rangeRemainingAfter.setListing(listing);
                    rangeRemainingAfter.setStartDate(bookingEndDate.plusDays(0));
                    rangeRemainingAfter.setEndDate(availableDates.getEndDate());
                    listingAvailableDatesRepository.save(rangeRemainingAfter);
                    listingAvailableDatesRepository.delete(availableDates);
                }
                //om bokningen är på slutet på range
                else if (bookingStartDate.isAfter(availableDates.getStartDate()) && bookingEndDate.isEqual(availableDates.getEndDate())) {
                    ListingAvailableDates rangeRemainingBefore = new ListingAvailableDates();
                    rangeRemainingBefore.setListing(listing);
                    rangeRemainingBefore.setStartDate(availableDates.getStartDate());
                    rangeRemainingBefore.setEndDate(bookingStartDate.minusDays(0));
                    listingAvailableDatesRepository.save(rangeRemainingBefore);
                    listingAvailableDatesRepository.delete(availableDates);

                }
            }
        }
    }



    //metod overloading
    public List<ListingAvailableDatesResponse> convertListingAvailableDatesEntityToListingAvailableDatesResponse(List<ListingAvailableDates> listingAvailableDatesList){
        List<ListingAvailableDatesResponse> listingAvailableDatesResponseList = new ArrayList<>();
        for(ListingAvailableDates listingAvailableDates : listingAvailableDatesList) {
            listingAvailableDatesResponseList.add(convertListingAvailableDatesEntityToListingAvailableDatesResponse(listingAvailableDates));
        }
        return listingAvailableDatesResponseList;
    }

    public ListingAvailableDatesResponse convertListingAvailableDatesEntityToListingAvailableDatesResponse(ListingAvailableDates listingAvailableDates) {
        ListingAvailableDatesResponse listingAvailableDatesResponse = new ListingAvailableDatesResponse();
        listingAvailableDatesResponse.setListingId(listingAvailableDates.getListing().getId());
        listingAvailableDatesResponse.setStartDate(listingAvailableDates.getStartDate());
        listingAvailableDatesResponse.setEndDate(listingAvailableDates.getEndDate());
        return listingAvailableDatesResponse;
    }

    public ListingAvailableDates convertListingAvailableDatesRequestToAvailableDatesEntity(ListingAvailableDatesRequest listingAvailableDatesRequest){
        ListingAvailableDates listingAvailableDates = new ListingAvailableDates();
        listingAvailableDates.setStartDate(listingAvailableDatesRequest.getStartDate());
        listingAvailableDates.setEndDate(listingAvailableDatesRequest.getEndDate());
        return listingAvailableDates;
    }

}
