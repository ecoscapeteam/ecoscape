package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Amenity;
import com.java2024.ecoscape.models.Category;
import com.java2024.ecoscape.models.Sustainability;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public class ListingRequest {


    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Location cannot be null")
    private String location;

    @NotNull(message = "Latitude cannot be null")
    private BigDecimal latitude;

    @NotNull(message = "Longitude cannot be null")
    private BigDecimal longitude;

    @NotNull(message = "Capacity cannot be null")
    private Integer capacity;

    private BigDecimal cleaningFee;

    @NotNull(message = "Price per night cannot be null")
    private BigDecimal pricePerNight;

    @NotNull(message = "Categories cannot be null")
    private Set<Category> categories;

    @NotNull(message = "Amenities cannot be null")
    private Set<Amenity> amenities;

    @NotNull(message = "Sustainabilities cannot be null")
    private Set<Sustainability> sustainabilities;

    @NotNull(message = "Listing rules can not be null")
    private ListingRulesDTO rules;

    public @NotNull(message = "Name cannot be null") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Name cannot be null") String name) {
        this.name = name;
    }

    public @NotNull(message = "Description cannot be null") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description cannot be null") String description) {
        this.description = description;
    }

    public @NotNull(message = "Location cannot be null") String getLocation() {
        return location;
    }

    public void setLocation(@NotNull(message = "Location cannot be null") String location) {
        this.location = location;
    }

    public @NotNull(message = "Latitude cannot be null") BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(@NotNull(message = "Latitude cannot be null") BigDecimal latitude) {
        this.latitude = latitude;
    }

    public @NotNull(message = "Longitude cannot be null") BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(@NotNull(message = "Longitude cannot be null") BigDecimal longitude) {
        this.longitude = longitude;
    }

    public @NotNull(message = "Capacity cannot be null") Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "Capacity cannot be null") Integer capacity) {
        this.capacity = capacity;
    }


    public BigDecimal getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(BigDecimal cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public @NotNull(message = "Price per night cannot be null") BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(@NotNull(message = "Price per night cannot be null") BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public @NotNull(message = "Categories cannot be null") Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(@NotNull(message = "Categories cannot be null") Set<Category> categories) {
        this.categories = categories;
    }

    public @NotNull(message = "Amenities cannot be null") Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(@NotNull(message = "Amenities cannot be null") Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public @NotNull(message = "Sustainabilities cannot be null") Set<Sustainability> getSustainabilities() {
        return sustainabilities;
    }

    public void setSustainabilities(@NotNull(message = "Sustainabilities cannot be null") Set<Sustainability> sustainabilities) {
        this.sustainabilities = sustainabilities;
    }

    public @NotNull(message = "Listing rules can not be null") ListingRulesDTO getRules() {
        return rules;
    }

    public void setRules(@NotNull(message = "Listing rules can not be null") ListingRulesDTO rules) {
        this.rules = rules;
    }
}