package com.java2024.ecoscape.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY) // each booking belongs to one user, but a user can have many booking
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user; // foreign key to User table
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)// each booking belongs to one listing, but a listing can have many booking
    @JsonIgnore
    @JoinColumn(name = "listing_id")
    private Listing listing;  // foreign key Listing table

    @Column(name = ("first_name"))
    //@NotBlank(message = "First name canot be null.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only letters and spaces are allowed.")
    @Size(max = 50, message = "Your first name cannot be longer than 50 characters.")
    private String firstName;

    @Column(name = ("last_name"))
    // @NotBlank(message = "Last name cannot be null.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only letters and spaces are allowed.")
    @Size(max = 50, message = "Your last name cannot be longer than 50 characters.")
    private String lastName;

    @Column(name = ("phone_number"))
    @NotNull(message = "Phone number cannot be null.")
    @Pattern(regexp = "^\\+\\d{1,3}\\d{9}$", message = "That's not a valid phone number.")
    private String usersContactPhoneNumber;

    @Column(name = ("email"))
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "That's not a valid email.")
    @NotNull(message = "Email cannot be null.")
    @Size(max = 30)
    private String usersContactEmail;


    @Column(name = ("start_date"))
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = ("end_date"))
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(name = ("status"))
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status cannot be null.")
    private Status status; // enum class

    @Column(name = ("guests"))
    @NotNull(message = "Guests cannot be null.")
    @Min(1)  // Minimum 1 guest
    @Max(10)
    private int guests;

    @Column(name = ("websites_fee"))
    @Transient
    private BigDecimal websitesFee;

    @Column(name = ("total_price"))
    private BigDecimal totalPrice;

    public Booking() {
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

    public void setUser(User user) {
        this.user = user;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsersContactEmail() {
        return usersContactEmail;
    }

    public void setUsersContactEmail(String email) {
        this.usersContactEmail = email;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public BigDecimal getWebsitesFee() {
        return websitesFee;
    }

    public void setWebsitesFee(BigDecimal websitesFee) {
        this.websitesFee = websitesFee;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUsersContactPhoneNumber(String usersContactPhoneNumber) {
        return this.usersContactPhoneNumber;
    }

    public void setUsersContactPhoneNumber(String usersContactPhoneNumber) {
        this.usersContactPhoneNumber = usersContactPhoneNumber;
    }


}

