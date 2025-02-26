package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Listing;

public class ListingImagesRequest {
    private Listing listing;
    private String imageUrl;

    public ListingImagesRequest(Listing listing, String imageUrl) {
        this.listing = listing;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
}

