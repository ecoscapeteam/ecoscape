package com.java2024.ecoscape.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Set;


@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue
    private Long id;


    // user_id FOREIGN KEY här, refererar till kolumnen user_id i listing tabellen
    @ManyToOne(fetch = FetchType.EAGER)
    // listing many to one gentemot user, user kan ha flera listings, en listing kan tillhöra bara en user, "laddar" hela usern direkt
    // user_id FOREIGN KEY här, refererar till kolumnen user_id i listing tabellen
    @JoinColumn(name = "user_id")
    //kan inte vara null, eftersom varje listing måste vara bunden till user
    @NotNull(message = "Listing owner can not be null")
    private User user;

    //kan inte vara null, eftersom varje listing måste ha namn
    @NotNull(message = "Listing name can not be null")
    //namn kan inte vara tömm sträng
    @NotEmpty(message = "Listing name can not be empty")
    //Tillåter inte fler än 100 characters
    @Length(max = 100, message = "Listing name can not exceed 100 characters")
    //Fältet får endast innehålla bokstäver (A-Z, a-z), siffror (0-9), mellanslag, bindestreck (-) och ampersand (&)
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-\\&]+$", message = "Invalid name! Only letters, numbers, spaces, hyphens, and ampersands are allowed.")
    private String name;

    //Beskrivning kan ej vara null
    @NotNull(message = "Listing description can not be null")
    //Kan ej vara tömm sträng
    @NotEmpty(message = "Listing description can not be empty")
    //Endast bbokstäver, siffror, mellanslag och vissa skiljetecken som kommatecken, punkter och frågetecken tillåts
    @Pattern(regexp = "^[A-Za-z0-9\\s\\.,!?\'\"\\(\\)\\-\\&\\#\\*\\+\\=]*$",
            message = "Invalid description! Only letters, numbers, spaces, commas, periods, exclamation marks, question marks, and other specified characters are allowed.")
    private String description;

    //Lokation kan inte vara null
    @NotNull(message = "Location can not be null")
    //Lokation kan inte vara tömm sträng
    @NotEmpty(message = "Location can not be empty")
    //Kan inte vara längre än 100 characters
    @Length(max = 100, message = "Listing location name cannot exceed 100 characters")
    private String location;


    //Kan vara mellan -90.0 och 90
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    //Kan inte vara null
    @NotNull(message = "Latitude can not be null")
    @Column(precision = 10, scale = 6)
    private BigDecimal latitude;

    //Kan vara mellan -180 och 180
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    //Kan inte vara null
    @NotNull(message = "Latitude can not be null")
    //Antal siffror i talet ska vara 10, 6 tecken efter komma, ger hög precision, vilket vi behöver när det gäller location
    @Column(precision = 10, scale = 6)
    private BigDecimal longitude;

    //kapaciteten ska vara minst 1, så att inte man kan lägga listing som ingen ska kunna boka
    @Min(value = 1, message = "The capacity must be at least 1")
    //kapaciteten ska vara max 10
    @Max(value = 10, message = "The value must not exceed 10")
    //kapacitet ska inte vara null
    @NotNull(message = "Capacity can not be null")
    private Integer capacity;

    //städavgiften är optional, dvs kan vara null, men skall vara positivt tal
    @Positive(message = "Cleaning fee must be a positive value")
    @Column(nullable = true)
    private BigDecimal cleaningFee;

    //Pris ska inte vara null, måste vara positiv tal
    @Positive(message = "Price per night must be a positive value")
    @NotNull(message = "Price per night can not be null")
    private BigDecimal pricePerNight;

    // rules är one to one gentemot listing, listing har unik rules "samling", den unika "samlingen" av rules gäller bara för den listingen,
    // "laddar" hela allt data från rules table
    @OneToOne(fetch = FetchType.EAGER)
    //rules_id FOREIGN KEY här, refererar till kolumnen rules_id i listing tabellen
    @JoinColumn(name = "rules_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Rules can not be null")
    private Rules rules;

    //storleken på set ska vara minst 1, dvs att listingen ska matcha minst en kategori
    @Size(min = 1)
    //user kan ha en eller flera kategorier, kategories är inga entities, utan enums, därför ska inte relation definieras
    //enum kategorier sparas som stärng, det gör enum kategorierna mer läsbar
    //kategorierna lagras i tabellen categories och listing_id kopplar varje category till en specifik Listing.
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "categories",
            joinColumns = @JoinColumn(name = "listing_id")
    )
    @Column(name = "category_enum")
    private Set<Category> categories;

    //storleken på set ska vara minst 1, dvs att listingen ska ha minst 1 bekvämlighet
    @Size(min = 1)
    //user kan ha en eller flera amenities, amenities är inga entities, utan enums, därför ska inte relation definieras
    //enum kategorier sparas som stärng, det gör enum kategorierna mer läsbar
    //bekvämligheterna lagras i tabellen amenities och listing_id kopplar varje bekvämlighet till en specifik Listing.
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "amenities",
            joinColumns = @JoinColumn(name = "listing_id")
    )
    @Column(name = "amenity_enum")
    private Set<Amenity> amenities;

    //storleken på set ska vara minst 1, dvs att listingen ska ha minst 1 sustainability
    @Size(min = 1)
    //user kan ha en eller flera sustainability, sustainability är inga entities, utan enums, därför ska inte relation definieras
    //enum kategorier sparas som stärng, det gör enum kategorierna mer läsbar
    //sustainabilities lagras i tabellen sustainabilities och listing_id kopplar varje sustanability till en specifik Listing.
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

    public Long getId() {
        return id;
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

    public @Positive(message = "Cleaning fee must be a positive value") BigDecimal getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(@Positive(message = "Cleaning fee must be a positive value") BigDecimal cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(@NotNull(message = "Rules cannot be null") Rules rules) {
        this.rules = rules;
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

    public @Size(min = 1) Set<Sustainability> getSustainabilities() {
        return sustainabilities;
    }

    public void setSustainabilities(@Size(min = 1) Set<Sustainability> sustainabilities) {
        this.sustainabilities = sustainabilities;
    }


}
