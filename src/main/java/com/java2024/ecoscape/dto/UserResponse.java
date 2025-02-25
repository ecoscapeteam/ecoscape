package com.java2024.ecoscape.dto;

import java.time.LocalDate;

public class UserResponse {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private String photoUrl;
    private LocalDate birthDate;
    private String contactPhoneNumber;
    private String contactEmail;

    public UserResponse(long id, String username, String firstName, String lastName, String bio, String photoUrl, LocalDate birthDate, String contactPhoneNumber, String contactEmail) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.photoUrl = photoUrl;
        this.birthDate = birthDate;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBio() {
        return bio;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
