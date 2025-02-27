package com.java2024.ecoscape.dto;

public class ListingImagesUploadResponse {
    private String imageUrl;
    private String message;

    public ListingImagesUploadResponse(String imageUrl, String message) {
        this.imageUrl = imageUrl;
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
