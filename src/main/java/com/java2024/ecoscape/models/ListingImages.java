package com.java2024.ecoscape.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "listings")
public class ListingImages {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    @NotEmpty(message = "Listing id cannot be empty.")
    private Listing listing;

    @Column(name = "image_url", unique = true)
    @Pattern(regexp = "\\.(jpg|jpeg|png)$", message = "Only jpg, jpeg and png images are allowed to be uploaded.")
    @NotEmpty(message = "Image url cannot be empty.")
    private String imageUrl;
}