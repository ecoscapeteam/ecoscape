package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.Rules;

public class CreateListingRequest {
    Listing listing;
    Rules rules;

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }
}
