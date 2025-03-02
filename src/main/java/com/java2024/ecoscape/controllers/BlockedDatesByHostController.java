package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.BlockedDatesByHostRequest;
import com.java2024.ecoscape.dto.BlockedDatesByHostResponse;
import com.java2024.ecoscape.models.BlockedDatesByHost;
import com.java2024.ecoscape.repository.BlockedDatesByHostRepository;
import com.java2024.ecoscape.services.BlockedDatesByHostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/blocked_dates_by_host")
public class BlockedDatesByHostController {

    private final BlockedDatesByHostService blockedDatesByHostService;
    private final BlockedDatesByHostRepository blockedDatesByHostRepository;

    public BlockedDatesByHostController(BlockedDatesByHostService blockedDatesByHostService, BlockedDatesByHostRepository blockedDatesByHostRepository) {
        this.blockedDatesByHostService = blockedDatesByHostService;
        this.blockedDatesByHostRepository = blockedDatesByHostRepository;
    }

    @PostMapping("/{listingId}")
    public ResponseEntity<BlockedDatesByHostResponse> createBlockedDatesByHost(@Valid @RequestBody BlockedDatesByHostRequest blockedDatesByHostRequest, @PathVariable Long listingId){
        BlockedDatesByHostResponse blockedDatesByHostResponse = blockedDatesByHostService.createBlockDatesByHost(listingId, blockedDatesByHostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(blockedDatesByHostResponse);
    }

    @GetMapping("/{blockedDatesByHostId}/getSingle")
    public ResponseEntity<BlockedDatesByHostResponse> getSingleBlockedDatesByHost (@PathVariable Long blockedDatesByHostId){
        BlockedDatesByHostResponse blockedDatesByHostResponse = blockedDatesByHostService.getSingleBlockDatesByHost(blockedDatesByHostId);
        return ResponseEntity.ok(blockedDatesByHostResponse);
    }

    @GetMapping("/{listingId}/getAll")
    public ResponseEntity<List<BlockedDatesByHostResponse>> getAllBlockedDatesByHostOfListing (@PathVariable Long listingId){
        List<BlockedDatesByHostResponse> listOfBlockedDatesByHostOfListing = blockedDatesByHostService.getAllBlockDatesOfAListing(listingId);
        return ResponseEntity.ok(listOfBlockedDatesByHostOfListing);
    }

    @DeleteMapping("/{blockedDatesByHostId}/deleteSingle")
    public ResponseEntity<String> deleteSingleBlockDatesByHost(@PathVariable Long blockedDatesByHostId) {
        BlockedDatesByHost blockedDatesByHost = blockedDatesByHostRepository.findById(blockedDatesByHostId).orElseThrow(() -> new NoSuchElementException("Blocked dates by host not found"));
        blockedDatesByHostService.deleteSingleBlockDatesByHost(blockedDatesByHostId);
        return ResponseEntity.ok("The dates from " + blockedDatesByHost.getStartDate() + " to " + blockedDatesByHost.getEndDate() + " are successfully unblocked!");
    }

    @DeleteMapping("/{listingId}/deleteAll")
    public ResponseEntity<String> deleteAllBlockedDatesByHostOfAListing (@PathVariable Long listingId){
        String resultMessage = blockedDatesByHostService.deleteAllBlockedDatesByHostOfAListing(listingId);
        return ResponseEntity.ok(resultMessage);
    }
}
