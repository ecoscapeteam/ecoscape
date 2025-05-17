package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.UserStatus;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserRequest {
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'\\s-]+$", message = "Invalid characters in first name")
    @Size(max = 50, message = "Your last name cannot be longer than 50 characters.")
    private String firstName;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'\\s-]+$", message = "Invalid characters in last name")
    @Size(max = 50, message = "Your last name cannot be longer than 50 characters.")
    private String lastName;

    @Size(max = 600)
    private String bio;
    private String photoUrl;
    private UserStatus status;
    private LocalDate birthDate;

    @Pattern(regexp = "^\\+\\d{1,3}\\d{9}$", message = "That's not a valid phone number.")
    private String contactPhoneNumber;

    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "That's not a valid email.")
    private String contactEmail;

    public UserRequest(String firstName, String lastName, String bio, String photoUrl, UserStatus status, LocalDate birthDate, String contactPhoneNumber, String contactEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.photoUrl = photoUrl;
        this.status = status;
        this.birthDate = birthDate;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getPhotoUrl() {
        return photoUrl;
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

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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
}
