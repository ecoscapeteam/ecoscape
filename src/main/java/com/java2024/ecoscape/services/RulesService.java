package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.ListingRulesDTO;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.Rules;
import com.java2024.ecoscape.repositories.ListingRepository;
import com.java2024.ecoscape.repositories.RulesRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RulesService {

    private final RulesRepository rulesRepository;
    private final ListingRepository listingRepository;

    public RulesService(RulesRepository rulesRepository, ListingRepository listingRepository) {
        this.rulesRepository = rulesRepository;
        this.listingRepository = listingRepository;
    }

    public ListingRulesDTO partialUpdateRules (ListingRulesDTO listingRulesDTO, Long listingId){
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        Rules rules = listing.getRules();
        if (listingRulesDTO.getRulesText() != null) {
            rules.setRulesText(listingRulesDTO.getRulesText());
        }
        if (listingRulesDTO.getCheckInTime() != null) {
            rules.setCheckInTime(listingRulesDTO.getCheckInTime());
        }
        if (listingRulesDTO.getCheckOutTime() != null) {
            rules.setCheckOutTime(listingRulesDTO.getCheckOutTime());
        }
        if (listingRulesDTO.getQuiteHoursStart() != null) {
            rules.setQuiteHoursStart(listingRulesDTO.getQuiteHoursStart());
        }
        if (listingRulesDTO.getQuiteQuiteHoursStop() != null) {
            rules.setQuiteQuiteHoursStop(listingRulesDTO.getQuiteQuiteHoursStop());
        }
        if (listingRulesDTO.getSmokingAllowed() != null) {
            rules.setSmokingAllowed(listingRulesDTO.getSmokingAllowed());
        }
        if (listingRulesDTO.getPartyingAllowed() != null) {
            rules.setPartyingAllowed(listingRulesDTO.getPartyingAllowed());
        }
        if (listingRulesDTO.getLoudMusicAllowed() != null) {
            rules.setLoudMusicAllowed(listingRulesDTO.getLoudMusicAllowed());
        }
        if (listingRulesDTO.getPetsAllowed() != null) {
            rules.setPetsAllowed(listingRulesDTO.getPetsAllowed());
        }

        if (listingRulesDTO.getSelfCheckingPossible() != null) {
            rules.setSelfCheckingPossible(listingRulesDTO.getSelfCheckingPossible());
        }
        if (listingRulesDTO.getIdRequiredUponCheckIn() != null) {
            rules.setIdRequiredUponCheckin(listingRulesDTO.getIdRequiredUponCheckIn());
        }
        Rules updatedRules = rulesRepository.save(rules);
        return convertRulesEntityToListingRulesDTO(updatedRules);



    }

    public Rules convertListingRulesDTOToRulesEntity(ListingRulesDTO listingRulesDTO) {
        Rules rules = new Rules();
        rules.setRulesText(listingRulesDTO.getRulesText());
        rules.setCheckInTime(listingRulesDTO.getCheckInTime());
        rules.setCheckOutTime(listingRulesDTO.getCheckOutTime());
        rules.setQuiteHoursStart(listingRulesDTO.getQuiteHoursStart());
        rules.setQuiteQuiteHoursStop(listingRulesDTO.getQuiteQuiteHoursStop());
        rules.setSmokingAllowed(listingRulesDTO.getSmokingAllowed());
        rules.setPartyingAllowed(listingRulesDTO.getPartyingAllowed());
        rules.setLoudMusicAllowed(listingRulesDTO.getLoudMusicAllowed());
        rules.setPetsAllowed(listingRulesDTO.getPetsAllowed());
        rules.setIdRequiredUponCheckin(listingRulesDTO.getIdRequiredUponCheckIn());
        rules.setSelfCheckingPossible(listingRulesDTO.getSelfCheckingPossible());
        return rules;
    }

    public ListingRulesDTO convertRulesEntityToListingRulesDTO(Rules rules) {
        ListingRulesDTO listingRulesDTO = new ListingRulesDTO();
        listingRulesDTO.setPetsAllowed(rules.getPetsAllowed());
        listingRulesDTO.setCheckInTime(rules.getCheckInTime());
        listingRulesDTO.setCheckOutTime(rules.getCheckOutTime());
        listingRulesDTO.setIdRequiredUponCheckIn(rules.getIdRequiredUponCheckin());
        listingRulesDTO.setLoudMusicAllowed(rules.getLoudMusicAllowed());
        listingRulesDTO.setPartyingAllowed(rules.getPartyingAllowed());
        listingRulesDTO.setQuiteHoursStart(rules.getQuiteHoursStart());
        listingRulesDTO.setQuiteQuiteHoursStop(rules.getQuiteQuiteHoursStop());
        listingRulesDTO.setRulesText(rules.getRulesText());
        listingRulesDTO.setSelfCheckingPossible(rules.getSelfCheckingPossible());
        listingRulesDTO.setSmokingAllowed(rules.getSmokingAllowed());
        return listingRulesDTO;
    }

}
