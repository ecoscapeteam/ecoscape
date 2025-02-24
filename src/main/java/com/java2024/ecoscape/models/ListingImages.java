package com.java2024.ecoscape.models;

import jakarta.persistence.*;

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
    private String imageUrl;
}