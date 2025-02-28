package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.BlockedDatesByHostRequest;
import com.java2024.ecoscape.dto.BlockedDatesByHostResponse;
import com.java2024.ecoscape.services.BlockedDatesByHostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blocked_dates_by_host")
public class BlockedDatesByHostController {

    private final BlockedDatesByHostService blockedDatesByHostService;

    public BlockedDatesByHostController(BlockedDatesByHostService blockedDatesByHostService) {
        this.blockedDatesByHostService = blockedDatesByHostService;
    }

    @PostMapping("/{listingId}")
    public ResponseEntity<BlockedDatesByHostResponse> createBlockedDatesByHost(@Valid @RequestBody BlockedDatesByHostRequest blockedDatesByHostRequest, @PathVariable Long listingId){
        BlockedDatesByHostResponse blockedDatesByHostResponse = blockedDatesByHostService.createBlockDatesByHost(listingId, blockedDatesByHostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(blockedDatesByHostResponse);
    }
}
