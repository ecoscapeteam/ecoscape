package com.java2024.ecoscape.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.java2024.ecoscape.models.Amenity;
import com.java2024.ecoscape.models.Category;
import com.java2024.ecoscape.models.Sustainability;

import java.math.BigDecimal;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListingResponse {

    private Long id;

    private String name;

    private String description;

    private String location;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Integer capacity;

    private Integer pricePerNight;

    private Integer cleaningFee;

    private ListingRulesDTO rules;

    private Set<Amenity> amenities;

    private Set<Sustainability> sustainability;

    private Set<Category> categories;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ListingRulesDTO getRules() {
        return rules;
    }

    public void setRules(ListingRulesDTO rules) {
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

    public Integer getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(Integer cleaningFee) {
        this.cleaningFee = cleaningFee;
    }
}