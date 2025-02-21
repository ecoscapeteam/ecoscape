package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Amenity;
import com.java2024.ecoscape.models.Listing;
import com.java2024.ecoscape.models.Rules;
import com.java2024.ecoscape.models.Sustainability;

import java.util.Set;

public class ListingDTO {
    Listing listing;
    Rules rules;
    Set<Amenity> amenities;
    Set<Sustainability> sustainability;

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

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Set<Sustainability> getSustainability() {
        return sustainability;
    }

    public void setSustainability(Set<Sustainability> sustainability) {
        this.sustainability = sustainability;
    }
}

