package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class ListingRequest {
    // Write all the attributes of the listing here which I need to return in the response or get in the request

    @NotNull(message = "Listing details can not be null")
    private Listing listing;
    @NotNull(message = "Rule details can not be null")
    private Rules rules;
    // @NotNull(message = "Amenity details can not be null")
    private Set<Amenity> amenities;
    //@NotNull(message = "Sustainability details can not be null")
    private Set<Sustainability> sustainability;
    //@NotNull(message = "Listing category can not be null")
    private Set<Category> categories;

    public ListingRequest() {
    }

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


    public void setAmenities() {
        this.amenities = amenities;
    }

    public void setSustainability() {
        this.sustainability = sustainability;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories() {
        this.categories = categories;
    }
}




