package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.ListingRulesDTO;
import com.java2024.ecoscape.services.RulesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
public class RulesController {

    private final RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PatchMapping("/{listingId}")
    @PreAuthorize("hasAnyRole('HOST', 'ADMIN')")
    public ResponseEntity<ListingRulesDTO> partialUpdateRules(@PathVariable Long listingId, @Valid @RequestBody ListingRulesDTO rulesRequest) {
        ListingRulesDTO listingRulesDTO = rulesService.partialUpdateRules(rulesRequest, listingId);
        return ResponseEntity.ok(listingRulesDTO);
    }
}
