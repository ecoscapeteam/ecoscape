package com.java2024.ecoscape.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.Set;


@Entity
@Table(name = "listing")
public class Listing {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Listing owner can not be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Listing name can not be null")
    @NotEmpty(message = "Listing name can not be empty")
    @Length(max = 100, message = "Listing name can not exceed 100 characters")
    private String name;

    @NotNull(message = "Listing description can not be null")
    @NotEmpty(message = "Listing description can not be empty")
    private String description;

    @NotNull(message = "Location can not be null")
    @NotEmpty(message = "Location can not be empty")
    @Length(max = 100, message = "Listing location name cannot exceed 100 characters")
    private String location;

    @NotNull(message = "Latitude can not be null")
    private Double latitude;

    @NotNull(message = "Longitude can not be null")
    private Double longitude;

    @NotNull(message = "Capacity can not be null")
    private Integer capacity;

    private Integer cleaningFee;

    @NotNull(message = "Price per night can not be null")
    private Integer pricePerNight;

    @NotNull(message = "Rules id can not be null")
    private Long rulesId;

    @Size(min = 1)
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "categories",
            joinColumns = @JoinColumn(name = "listing_id")
    )
    @Column(name = "category_enum")
    private Set<Category> categories;

    @Size(min = 1)
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "amenities",
            joinColumns = @JoinColumn(name = "listing_id")
    )
    @Column(name = "amenity_enum")
    private Set<Amenity> amenities;

    @Size(min = 1)
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "sustainabilities",
            joinColumns = @JoinColumn(name = "listing_id")
    )
    @Column(name = "sustainabilities_enum")
    private Set<Sustainability> sustainabilities;

    public Listing() {
    }


    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser() {
        this.user = user;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(Integer cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public Integer getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Integer pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Long getRulesId() {
        return rulesId;
    }

    public void setRulesId(Long rulesId) {
        this.rulesId = rulesId;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Set<Sustainability> getSustainabilities() {
        return sustainabilities;
    }

    public void setSustainabilities(Set<Sustainability> sustainabilities) {
        this.sustainabilities = sustainabilities;
    }
}
