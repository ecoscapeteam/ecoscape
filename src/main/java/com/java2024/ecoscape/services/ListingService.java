package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.ListingResponse;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.Rules;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.repository.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final RulesService rulesService;
    private final UserService userService;



    public ListingService(ListingRepository listingRepository, RulesService rulesService, UserService userService) {
        this.listingRepository = listingRepository;
        this.rulesService = rulesService;
        this.userService = userService;
    }

    //metod för att spara Listing till Listing repositoriet
    public Listing saveListing(Listing listing) {
        return listingRepository.save(listing);
    }

    //metod för att validera en listing
    public void validateListing(Listing listing) {

        // Validera att namn inte är null eller tomt och följer mönstret
        if (listing.getName() == null || listing.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Listing name can not be empty or null");
        }
        if (listing.getName().length() > 100) {
            throw new IllegalArgumentException("Listing name can not exceed 100 characters");
        }
        if (!listing.getName().matches("^[A-Za-z0-9\\s\\-\\&]+$")) {
            throw new IllegalArgumentException("Invalid name! Only letters, numbers, spaces, hyphens, and ampersands are allowed.");
        }

        // Validera att beskrivning inte är null eller tom
        if (listing.getDescription() == null || listing.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Listing description can not be empty or null");
        }
        if (!listing.getDescription().matches("^[A-Za-z0-9\\s\\.,!?\'\"\\(\\)\\-\\&\\#\\*\\+\\=]*$")) {
            throw new IllegalArgumentException("Invalid description! Only letters, numbers, spaces, commas, periods, exclamation marks, question marks, and other specified characters are allowed.");
        }

        // Validera att lokation inte är null, tom och max 100 tecken
        if (listing.getLocation() == null || listing.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location can not be empty or null");
        }
        if (listing.getLocation().length() > 100) {
            throw new IllegalArgumentException("Listing location name cannot exceed 100 characters");
        }

        // Validera latitud och longitud
        if (listing.getLatitude() == null || listing.getLatitude().compareTo(new BigDecimal("-90.0")) < 0 || listing.getLatitude().compareTo(new BigDecimal("90.0")) > 0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (listing.getLongitude() == null || listing.getLongitude().compareTo(new BigDecimal("-180.0")) < 0 || listing.getLongitude().compareTo(new BigDecimal("180.0")) > 0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }

        // Validera kapacitet
        if (listing.getCapacity() == null || listing.getCapacity() < 1) {
            throw new IllegalArgumentException("The capacity must be at least 1");
        }
        if (listing.getCapacity() > 10) {
            throw new IllegalArgumentException("The value must not exceed 10");
        }

        // Validera städavgift om det är satt
        if (listing.getCleaningFee() != null && listing.getCleaningFee() <= 0) {
            throw new IllegalArgumentException("Cleaning fee must be a positive value");
        }

        // Validera pris per natt
        if (listing.getPricePerNight() == null || listing.getPricePerNight() <= 0) {
            throw new IllegalArgumentException("Price per night must be a positive value");
        }

        // Validera att det finns minst en kategori
        if (listing.getCategories() == null || listing.getCategories().size() < 1) {
            throw new IllegalArgumentException("Listing must have at least one category");
        }

        // Validera att det finns minst en bekvämlighet
        if (listing.getAmenities() == null || listing.getAmenities().size() < 1) {
            throw new IllegalArgumentException("Listing must have at least one amenity");
        }

        // Validera att det finns minst en hållbarhet
        if (listing.getSustainabilities() == null || listing.getSustainabilities().size() < 1) {
            throw new IllegalArgumentException("Listing must have at least one sustainability");
        }
    }

    //metod för att skaffa listing
    @Transactional
    public ListingResponse createListing(Long userId, Listing listingDetails, Rules rulesDetails) {
        User user = userService.findUserById(userId);

        rulesService.validateRules(rulesDetails);
        Rules savedRules = rulesService.saveRules(rulesDetails);

        validateListing(listingDetails);
        listingDetails.setUser(user);
        listingDetails.setRules(savedRules);

        Listing savedListing = saveListing(listingDetails);
        ListingResponse listingResponse = new ListingResponse();
        listingResponse.setId(savedListing.getId());
        listingResponse.setName(savedListing.getName());
        listingResponse.setDescription(savedListing.getDescription());
        listingResponse.setLocation(savedListing.getLocation());
        listingResponse.setLatitude(savedListing.getLatitude());
        listingResponse.setLongitude(savedListing.getLongitude());
        listingResponse.setCapacity(savedListing.getCapacity());
        listingResponse.setPricePerNight(savedListing.getPricePerNight());
        listingResponse.setRules(savedListing.getRules());
        listingResponse.setCategories(savedListing.getCategories());
        listingResponse.setSustainability(savedListing.getSustainabilities());
        listingResponse.setAmenities(savedListing.getAmenities());
        return listingResponse;

    }

    //metod för att hämta en listing efter listing id
    public ListingResponse findListingById(Long listingId) {
        Listing listingDetails = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        ListingResponse listingResponse = new ListingResponse();
        listingResponse.setId(listingDetails.getId());
        listingResponse.setName(listingDetails.getName());
        listingResponse.setDescription(listingDetails.getDescription());
        listingResponse.setLocation(listingDetails.getLocation());
        listingResponse.setLatitude(listingDetails.getLatitude());
        listingResponse.setLongitude(listingDetails.getLongitude());
        listingResponse.setCapacity(listingDetails.getCapacity());
        listingResponse.setPricePerNight(listingDetails.getPricePerNight());
        listingResponse.setRules(listingDetails.getRules());
        listingResponse.setCategories(listingDetails.getCategories());
        listingResponse.setSustainability(listingDetails.getSustainabilities());
        listingResponse.setAmenities(listingDetails.getAmenities());
        return listingResponse;

    }

    //metod för att uppdatera en listing
    /*public Listing partialUpdateListingById(Long listingId, Listing newListingDetails){
        Listing exisitngListing = findListingById(listingId);
        if (newListingDetails.getName() != null){
            exisitngListing.setName(newListingDetails.getName());
        }
        if (newListingDetails.getDescription() != null){
            exisitngListing.setDescription(newListingDetails.getDescription());
        }
        if (newListingDetails.getLocation() != null){
            exisitngListing.setLocation(newListingDetails.getLocation());
        }
        if (newListingDetails.getLatitude() != null){
            exisitngListing.setLatitude(newListingDetails.getLatitude());
        }
        if (newListingDetails.getLongitude() != null){
            exisitngListing.setLongitude(newListingDetails.getLongitude());
        }
        if (newListingDetails.getCapacity() != null){
            exisitngListing.setCapacity(newListingDetails.getCapacity());
        }
        if (newListingDetails.getCleaningFee() != null){
            exisitngListing.setCleaningFee(newListingDetails.getCleaningFee());
        }
        if (newListingDetails.getPricePerNight() != null){
            exisitngListing.setPricePerNight(newListingDetails.getPricePerNight());
        }
        if (newListingDetails.getRules() != null){
            exisitngListing.setRules(newListingDetails.getRules());
        }
        if (newListingDetails.getCategories() != null){
            exisitngListing.setCategories(newListingDetails.getCategories());
        }
        if (newListingDetails.getAmenities() != null){
            exisitngListing.setAmenities(newListingDetails.getAmenities());
        }
        if (newListingDetails.getSustainabilities() != null){
            exisitngListing.setSustainabilities(newListingDetails.getSustainabilities());
        }
        return saveListing(exisitngListing);
    }

    public void deleteListingById(Long listingId){
        Listing listing = findListingById(listingId);
        listingRepository.delete(listing);
    }

     */
}

