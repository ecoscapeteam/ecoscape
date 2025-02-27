package com.java2024.ecoscape.dto;

public class ListingImagesGetResponse {
    private Long listingId;
    private String imageUrl;

    public ListingImagesGetResponse(Long listingId, String imageUrl) {
        this.listingId = listingId;
        this.imageUrl = imageUrl;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
