package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.BlockedDatesByHostRequest;
import com.java2024.ecoscape.dto.BlockedDatesByHostResponse;
import com.java2024.ecoscape.models.BlockedDatesByHost;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.repository.BlockedDatesByHostRepository;
import com.java2024.ecoscape.repository.ListingRepository;
import org.springframework.stereotype.Service;

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

    public void deleteSingleBlockDatesByHost(Long blockDatesByHostId){
        BlockedDatesByHost blockedDatesByHost = blockedDatesByHostRepository.findById(blockDatesByHostId).orElseThrow(() -> new NoSuchElementException("No such blocked dates "));
        blockedDatesByHostRepository.delete(blockedDatesByHost);
    }

    public BlockedDatesByHostResponse convertBlockedDatesByHostEntityToBlockedDatesByHostDTO (BlockedDatesByHost blockedDatesByHost){
        BlockedDatesByHostResponse blockedDatesByHostResponse = new BlockedDatesByHostResponse();
        blockedDatesByHostResponse.setId(blockedDatesByHost.getId());
        blockedDatesByHostResponse.setListingId(blockedDatesByHost.getListing().getId());
        blockedDatesByHostResponse.setStartDate(blockedDatesByHost.getStartDate());
        blockedDatesByHostResponse.setEndDate(blockedDatesByHost.getEndDate());
        return blockedDatesByHostResponse;
    }
    public BlockedDatesByHost convertBlockedDatesByHostDTOtoBlockedDatesByHostEntity (BlockedDatesByHostRequest blockedDatesByHostRequest){
        BlockedDatesByHost blockedDatesByHost = new BlockedDatesByHost();
        blockedDatesByHost.setStartDate(blockedDatesByHostRequest.getStartDate());
        blockedDatesByHost.setEndDate(blockedDatesByHostRequest.getEndDate());
        return blockedDatesByHost;
    }

}
