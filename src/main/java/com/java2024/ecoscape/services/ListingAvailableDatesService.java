package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.ListingAvailableDatesRequest;
import com.java2024.ecoscape.dto.ListingAvailableDatesResponse;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.ListingAvailableDates;
import com.java2024.ecoscape.repositories.ListingAvailableDatesRepository;
import com.java2024.ecoscape.repositories.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        ListingAvailableDates listingAvailableDates = listingAvailableDatesRepository.findById(listingAvailableDatesId).orElseThrow(() -> new NoSuchElementException("No such available dates "));
        listingAvailableDates.setStartDate(newStartDate);
        listingAvailableDates.setEndDate(newEndDate);
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
        listingAvailableDatesResponse.setListingId(listingAvailableDates.getId());
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
