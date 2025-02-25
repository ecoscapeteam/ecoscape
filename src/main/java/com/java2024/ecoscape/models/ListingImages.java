package com.java2024.ecoscape.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "listing_images")
public class ListingImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @Column(name = "image_url", nullable = false)
    @Pattern(regexp = ".*\\.(jpg|jpeg|png)$", message = "Only jpg, jpeg and png images are allowed to be uploaded.")
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

