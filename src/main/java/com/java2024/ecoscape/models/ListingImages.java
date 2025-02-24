package com.java2024.ecoscape.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "listings")
public class ListingImages {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @Column(name = "image_url")
    @Pattern(regexp = "\\.(jpg|jpeg|png)$", message = "Only jpg, jpeg and png images are allowed to be uploaded.")
    private String imageUrl;
}