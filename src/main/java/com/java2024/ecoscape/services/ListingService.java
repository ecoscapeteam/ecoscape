package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.ListingRequest;
import com.java2024.ecoscape.dto.ListingResponse;
import com.java2024.ecoscape.dto.ListingRulesDTO;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.Rules;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.repository.ListingRepository;
import com.java2024.ecoscape.repository.RulesRepository;
import com.java2024.ecoscape.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final RulesRepository rulesRepository;
    private final UserRepository userRepository;
    private final RulesService rulesService;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository, RulesRepository rulesRepository, RulesService rulesService) {
        this.listingRepository = listingRepository;
        this.rulesRepository = rulesRepository;
        this.userRepository = userRepository;
        this.rulesService = rulesService;
    }

    //metod för att spara Listing till Listing repositoriet
    public Listing saveListing(Listing listing) {
        return listingRepository.save(listing);
    }

    //metod för att validera en listing
    public void validateListing(ListingRequest listingRequest) {

        // Validera att namn inte är null eller tomt och följer mönstret
        if (listingRequest.getName() == null || listingRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Listing name can not be empty or null");
        }
        if (listingRequest.getName().length() > 100) {
            throw new IllegalArgumentException("Listing name can not exceed 100 characters");
        }
        if (!listingRequest.getName().matches("^[A-Za-z0-9\\s\\-\\&]+$")) {
            throw new IllegalArgumentException("Invalid name! Only letters, numbers, spaces, hyphens, and ampersands are allowed.");
        }

        // Validera att beskrivning inte är null eller tom
        if (listingRequest.getDescription() == null || listingRequest.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Listing description can not be empty or null");
        }
        if (!listingRequest.getDescription().matches("^[A-Za-z0-9\\s\\.,!?\'\"\\(\\)\\-\\&\\#\\*\\+\\=]*$")) {
            throw new IllegalArgumentException("Invalid description! Only letters, numbers, spaces, commas, periods, exclamation marks, question marks, and other specified characters are allowed.");
        }

        // Validera att lokation inte är null, tom och max 100 tecken
        if (listingRequest.getLocation() == null || listingRequest.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location can not be empty or null");
        }
        if (listingRequest.getLocation().length() > 100) {
            throw new IllegalArgumentException("Listing location name cannot exceed 100 characters");
        }

        // Validera latitud och longitud
        if (listingRequest.getLatitude() == null || listingRequest.getLatitude().compareTo(new BigDecimal("-90.0")) < 0 || listingRequest.getLatitude().compareTo(new BigDecimal("90.0")) > 0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (listingRequest.getLongitude() == null || listingRequest.getLongitude().compareTo(new BigDecimal("-180.0")) < 0 || listingRequest.getLongitude().compareTo(new BigDecimal("180.0")) > 0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }

        // Validera kapacitet
        if (listingRequest.getCapacity() == null || listingRequest.getCapacity() < 1) {
            throw new IllegalArgumentException("The capacity must be at least 1");
        }
        if (listingRequest.getCapacity() > 10) {
            throw new IllegalArgumentException("The value must not exceed 10");
        }

        // Validera städavgift om det är satt
        if  (listingRequest.getCleaningFee() != null && listingRequest.getCleaningFee().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cleaning fee must be a positive value");
        }

        // Validera pris per natt
        if (listingRequest.getPricePerNight() == null || listingRequest.getPricePerNight().compareTo(BigDecimal.ZERO) <= 0)  {
            throw new IllegalArgumentException("Price per night must be a positive value");
        }

        // Validera att det finns minst en kategori
        if (listingRequest.getCategories() == null || listingRequest.getCategories().size() < 1) {
            throw new IllegalArgumentException("Listing must have at least one category");
        }

        // Validera att det finns minst en bekvämlighet
        if (listingRequest.getAmenities() == null || listingRequest.getAmenities().size() < 1) {
            throw new IllegalArgumentException("Listing must have at least one amenity");
        }

        // Validera att det finns minst en hållbarhet
        if (listingRequest.getSustainabilities() == null || listingRequest.getSustainabilities().size() < 1) {
            throw new IllegalArgumentException("Listing must have at least one sustainability");
        }

        if (listingRequest.getRules().getRulesText() != null) {
            if (listingRequest.getRules().getRulesText().length() > 250) {
                throw new IllegalArgumentException("Rules text cannot exceed 250 characters.");
            }
            if (!listingRequest.getRules().getRulesText().matches("^[A-Za-z0-9\\s\\.,!?\'\"\\(\\)\\-\\&\\#\\*\\+\\=]*$")) {
                throw new IllegalArgumentException("Invalid rule description! Only letters, numbers, spaces, commas, periods, exclamation marks, question marks, and other specified characters are allowed.");
            }
        }

        if (listingRequest.getRules().getCheckInTime() == null || !listingRequest.getRules().getCheckInTime().matches("^(0[0-9]|1[0-9]|2[0-3]):00$")) {
            throw new IllegalArgumentException("Invalid check-in time format! The time must be in the format HH:00, where HH is between 00 and 23.");
        }

        if (listingRequest.getRules().getCheckOutTime() == null || !listingRequest.getRules().getCheckOutTime().matches("^(0[0-9]|1[0-9]|2[0-3]):00$")) {
            throw new IllegalArgumentException("Invalid check-out time format! The time must be in the format HH:00, where HH is between 00 and 23.");
        }

        if (listingRequest.getRules().getQuiteHoursStart() != null && !listingRequest.getRules().getQuiteHoursStart().matches("^(0[0-9]|1[0-9]|2[0-3]):00$")) {
            throw new IllegalArgumentException("Invalid quiet hours start time format! The time must be in the format HH:00, where HH is between 00 and 23.");
        }

        if (listingRequest.getRules().getQuiteQuiteHoursStop() != null && !listingRequest.getRules().getQuiteQuiteHoursStop().matches("^(0[0-9]|1[0-9]|2[0-3]):00$")) {
            throw new IllegalArgumentException("Invalid quiet hours stop time format! The time must be in the format HH:00, where HH is between 00 and 23.");
        }

        if (listingRequest.getRules().getSmokingAllowed() != null && !(listingRequest.getRules().getSmokingAllowed() instanceof Boolean)) {
            throw new IllegalArgumentException("Smoking allowed must be a boolean value (true/false).");
        }

        if (listingRequest.getRules().getPartyingAllowed() != null && !(listingRequest.getRules().getPartyingAllowed() instanceof Boolean)) {
            throw new IllegalArgumentException("Partying allowed must be a boolean value (true/false).");
        }

        if (listingRequest.getRules().getLoudMusicAllowed() != null && !(listingRequest.getRules().getLoudMusicAllowed() instanceof Boolean)) {
            throw new IllegalArgumentException("Loud music allowed must be a boolean value (true/false).");
        }

        if (listingRequest.getRules().getArePetsAllowed() != null && !(listingRequest.getRules().getArePetsAllowed() instanceof Boolean)) {
            throw new IllegalArgumentException("Pets allowed must be a boolean value (true/false).");
        }

        if (listingRequest.getRules().getSelfCheckingPossible() != null && !(listingRequest.getRules().getSelfCheckingPossible() instanceof Boolean)) {
            throw new IllegalArgumentException("Self-checking possible must be a boolean value (true/false).");
        }

        if (listingRequest.getRules().getIdRequiredUponCheckIn() != null && !(listingRequest.getRules().getIdRequiredUponCheckIn() instanceof Boolean)) {
            throw new IllegalArgumentException("ID required upon check-in must be a boolean value (true/false).");
        }
    }

    //metod för att skaffa listing
    @Transactional
    public ListingResponse createListing(Long userId, ListingRequest listingRequest) {
//hittar userb
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
//validerar requesten för att skaffa en listing
        validateListing(listingRequest);
//tar rules från listing från listing request och konvertar det till rules entity och sparar till rulesrepo
        Rules rules = rulesService.convertListingRulesDTOToRulesEntity(listingRequest.getRules());
        rulesRepository.save(rules);
//konvertar hela listing requesten och konverterar det till listing entity
        Listing listing = convertListingRequestToListingEntity(listingRequest);
        //ger listingen en user attribut
        listing.setUser(user);
        //get listingen rules atribut
        listing.setRules(rules);
//sprar listingen till repo
        Listing savedListing = saveListing(listing);
//konverterar listing entitetet till listing response dto
        return convertListingEntityToListingResponse(savedListing);
    }



    //metod för att hämta en listing efter listing id
    public ListingResponse getListingById(Long listingId) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        return convertListingEntityToListingResponse(listing);
    }

  //metod för att uppdatera en listing
    public ListingResponse partialUpdateListingById(Long listingId, ListingRequest listingRequest){
        //hittar listingen
        Listing existingListing = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        //om det finns ett namn i requesten  get listing entitetet nya namnet
        if (listingRequest.getName() != null){
            existingListing.setName(listingRequest.getName());
        }
        //om det finns ett beskrivning i requesten  get listing entitetet nya beskrivning
        if (listingRequest.getDescription() != null){
            existingListing.setDescription(listingRequest.getDescription());
        }
        //om det finns ett location i requesten  get listing entitetet nya location
        if (listingRequest.getLocation() != null){
            existingListing.setLocation(listingRequest.getLocation());
        }
        //om det finns ett latitude i requesten  get listing entitetet nya latituden
        if (listingRequest.getLatitude() != null){
            existingListing.setLatitude(listingRequest.getLatitude());
        }
        //om det finns ett longtitude i requesten  get listing entitetet nya latituden
        if (listingRequest.getLongitude() != null){
            existingListing.setLongitude(listingRequest.getLongitude());
        }
        //om det finns ett kapacitet i requesten  get listing entitetet nya kapaciteten
        if (listingRequest.getCapacity() != null){
            existingListing.setCapacity(listingRequest.getCapacity());
        }
        //om det finns ett cleaningfee i requesten  get listing entitetet nya cleaningfee
        if (listingRequest.getCleaningFee() != null){
            existingListing.setCleaningFee(listingRequest.getCleaningFee());
        }
        //om det finns ett pris pet natt i requesten  get listing entitetet nya pris per natt
        if (listingRequest.getPricePerNight() != null){
            existingListing.setPricePerNight(listingRequest.getPricePerNight());
        }
        //om det finns kategorier i requesten  get listing entitetet nya kategorier
        if (listingRequest.getCategories() != null){
            existingListing.setCategories(listingRequest.getCategories());
        }
        //om det finns ammenities  i requesten  get listing entitetet nya amenities
        if (listingRequest.getAmenities() != null){
            existingListing.setAmenities(listingRequest.getAmenities());
        }
        //om det finns sustainabilities i requesten  get listing entitetet nya sustainabilities
        if (listingRequest.getSustainabilities() != null){
            existingListing.setSustainabilities(listingRequest.getSustainabilities());
        }

        Listing updatedListing = listingRepository.save(existingListing);
        return convertListingEntityToListingResponse(updatedListing);
    }


    //ta bort en listing

    public ListingResponse convertListingEntityToListingResponse(Listing listing) {
        ListingResponse listingResponse = new ListingResponse();
        listingResponse.setId(listing.getUser().getId());
        listingResponse.setCleaningFee(listing.getCleaningFee());
        listingResponse.setSustainability(listing.getSustainabilities());
        listingResponse.setAmenities(listing.getAmenities());
        listingResponse.setCategories(listing.getCategories());
        listingResponse.setPricePerNight(listing.getPricePerNight());
        listingResponse.setLongitude(listing.getLongitude());
        listingResponse.setLatitude(listing.getLatitude());
        listingResponse.setDescription(listing.getDescription());
        listingResponse.setName(listing.getName());
        listingResponse.setCapacity(listing.getCapacity());
        listingResponse.setLocation(listing.getLocation());
        ListingRulesDTO listingRulesDTO = rulesService.convertRulesEntityToListingRulesDTO(listing.getRules());
        listingResponse.setRules(listingRulesDTO);
        return listingResponse;
    }

    public Listing convertListingRequestToListingEntity(ListingRequest listingRequest) {
        Listing listing = new Listing();
        listing.setCleaningFee(listingRequest.getCleaningFee());
        listing.setSustainabilities(listingRequest.getSustainabilities());
        listing.setAmenities(listingRequest.getAmenities());
        listing.setCategories(listingRequest.getCategories());
        listing.setPricePerNight(listingRequest.getPricePerNight());
        listing.setLongitude(listingRequest.getLongitude());
        listing.setLatitude(listingRequest.getLatitude());
        listing.setDescription(listingRequest.getDescription());
        listing.setName(listingRequest.getName());
        listing.setCapacity(listingRequest.getCapacity());
        listing.setLocation(listingRequest.getLocation());
        return listing;
    }


    //metod for att ta bort en listing
    public void deleteListingById(Long id){
        Listing listing = listingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Lisitng not found"));
        listingRepository.delete(listing);
    }


}

