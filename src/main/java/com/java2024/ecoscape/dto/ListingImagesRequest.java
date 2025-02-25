package com.java2024.ecoscape.dto;

public class ListingImagesRequest {
    private String imageUrl;

    public ListingImagesRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

