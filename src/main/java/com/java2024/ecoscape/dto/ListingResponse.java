package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Amenity;
import com.java2024.ecoscape.models.Category;
import com.java2024.ecoscape.models.Rules;
import com.java2024.ecoscape.models.Sustainability;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public class ListingResponse {

    private Long id;

    @NotNull(message = "Listing name details can not be null")
    private String name;

    @NotNull(message = "Listing description can not be null")
    private String description;

    @NotNull(message = "Listing location can not be null")
    private String location;

    @NotNull(message = "Listing latitude can not be null")
    private BigDecimal latitude;

    @NotNull(message = "Listing longitude can not be null")
    private BigDecimal longitude;

    @NotNull(message = "Listing capacity can not be null")
    private Integer capacity;

    @NotNull(message = "Price per night can not be null")
    private Integer pricePerNight;

    @NotNull(message = "Rule details can not be null")
    private Rules rules;

    @NotNull(message = "Amenity details can not be null")
    private Set<Amenity> amenities;

    @NotNull(message = "Sustainability details can not be null")
    private Set<Sustainability> sustainability;

    @NotNull(message = "Category details can not be null")
    private Set<Category> categories;

    // Default constructor
    public ListingResponse() {
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Integer pricePerNight) {
        this.pricePerNight = pricePerNight;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

