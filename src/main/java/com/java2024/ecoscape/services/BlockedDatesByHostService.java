package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.BlockedDatesByHostRequest;
import com.java2024.ecoscape.dto.BlockedDatesByHostResponse;
import com.java2024.ecoscape.models.BlockedDatesByHost;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.repository.BlockedDatesByHostRepository;
import com.java2024.ecoscape.repository.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BlockedDatesByHostService {
    private final BlockedDatesByHostRepository blockedDatesByHostRepository;
    private final ListingRepository listingRepository;

    public BlockedDatesByHostService(BlockedDatesByHostRepository blockedDatesByHostRepository, ListingRepository listingRepository) {
        this.blockedDatesByHostRepository = blockedDatesByHostRepository;
        this.listingRepository = listingRepository;
    }

    public BlockedDatesByHostResponse createBlockDatesByHost(Long listingId, BlockedDatesByHostRequest blockedDatesByHostRequest){
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new NoSuchElementException("Listing not found"));
        BlockedDatesByHost blockedDatesByHost = convertBlockedDatesByHostDTOtoBlockedDatesByHostEntity(blockedDatesByHostRequest);
        blockedDatesByHost.setListing(listing);
        BlockedDatesByHost savedBlockedDatesByHost = blockedDatesByHostRepository.save(blockedDatesByHost);
        return convertBlockedDatesByHostEntityToBlockedDatesByHostDTO(savedBlockedDatesByHost);
    }

    public BlockedDatesByHostResponse getSingleBlockDatesByHost(Long blockDatesByHostId){
        BlockedDatesByHost blockedDatesByHost = blockedDatesByHostRepository.findById(blockDatesByHostId).orElseThrow(() -> new NoSuchElementException("No such blocked dates "));
        return convertBlockedDatesByHostEntityToBlockedDatesByHostDTO(blockedDatesByHost);
    }

    public List<BlockedDatesByHostResponse> getAllBlockDatesOfAListing(Long listingId){
        List <BlockedDatesByHost> blockedDatesByHostList = blockedDatesByHostRepository.findAllByListingId(listingId);
        return convertBlockedDatesByHostEntityToBlockedDatesByHostDTO(blockedDatesByHostList);
    }

    public void deleteSingleBlockDatesByHost(Long blockDatesByHostId){
        BlockedDatesByHost blockedDatesByHost = blockedDatesByHostRepository.findById(blockDatesByHostId).orElseThrow(() -> new NoSuchElementException("No such blocked dates "));
        blockedDatesByHostRepository.delete(blockedDatesByHost);
    }

    //behöver Transactional annotation för metoden ska funka, funkade inte utan den
    @Transactional
    public String deleteAllBlockedDatesByHostOfAListing(Long listingId) {
        listingRepository.findById(listingId)
                .orElseThrow(() -> new NoSuchElementException("Listing not found"));

        if (blockedDatesByHostRepository.existsByListingId(listingId)) {
            blockedDatesByHostRepository.deleteAllByListingId(listingId);
            return "All blocked dates for listing with ID " + listingId + " have been successfully removed.";
        } else {
            return "No blocked dates found for listing ID " + listingId;
        }
    }


    public BlockedDatesByHostResponse convertBlockedDatesByHostEntityToBlockedDatesByHostDTO (BlockedDatesByHost blockedDatesByHost){
        BlockedDatesByHostResponse blockedDatesByHostResponse = new BlockedDatesByHostResponse();
        blockedDatesByHostResponse.setId(blockedDatesByHost.getId());
        blockedDatesByHostResponse.setListingId(blockedDatesByHost.getListing().getId());
        blockedDatesByHostResponse.setStartDate(blockedDatesByHost.getStartDate());
        blockedDatesByHostResponse.setEndDate(blockedDatesByHost.getEndDate());
        return blockedDatesByHostResponse;
    }

    //metod overloading
    public List<BlockedDatesByHostResponse> convertBlockedDatesByHostEntityToBlockedDatesByHostDTO (List<BlockedDatesByHost> blockedDatesByHostList){
        List<BlockedDatesByHostResponse> blockedDatesByHostResponseList = new ArrayList<>();
        for(BlockedDatesByHost blockedDatesByHost: blockedDatesByHostList) {
            blockedDatesByHostResponseList.add(convertBlockedDatesByHostEntityToBlockedDatesByHostDTO(blockedDatesByHost));
        }
        return blockedDatesByHostResponseList;
    }

    public BlockedDatesByHost convertBlockedDatesByHostDTOtoBlockedDatesByHostEntity (BlockedDatesByHostRequest blockedDatesByHostRequest){
        BlockedDatesByHost blockedDatesByHost = new BlockedDatesByHost();
        blockedDatesByHost.setStartDate(blockedDatesByHostRequest.getStartDate());
        blockedDatesByHost.setEndDate(blockedDatesByHostRequest.getEndDate());
        return blockedDatesByHost;
    }

}
