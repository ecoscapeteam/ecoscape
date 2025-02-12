package com.java2024.ecoscape.models;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
import java.util.Date;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.models.Status;


import javax.xml.crypto.Data;

@Entity
@Table(name = "bookings")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_Id")
    private Long id;
    @ManyToOne // each booking belongs to one user, but a user can have many booking
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user; // foreign key to User table
    //@ManyToOne // each booking belongs to one listing, but a listing can have many booking
   // @JoinColumn(name = "listing_id", referencedColumnName = "listing_id")
   // private Listing listing;  // foreign key Listing table

    @Column(name = ("first_name"))
    private String firstName;

    @Column(name = ("lastname"))
    private String lastName;

    @Column(name = ("phone"))
    private String phone;

    @Column(name = ("email"))
    private String email;

    @Column(name = ("city"))
    private String city;

    @Column(name = ("start_date"))
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = ("end_date"))
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = ("status"))
    @Enumerated(EnumType.STRING)
    private Status status; // enum class

    @Column(name = ("guests"))
    private int guests;

    @Column(name = ("websites_fee"))
    private double websitesFee;

    @Column(name = ("total_price"))
    private double totalPrice;

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

   /* public Listing getListing() {
        return listing;
    }

   // public void setListing(Listing listing) {
        this.listing = listing;
    }*/

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public double getWebsitesFee() {
        return websitesFee;
    }

    public void setWebsitesFee(double websitesFee) {
        this.websitesFee = websitesFee;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}



