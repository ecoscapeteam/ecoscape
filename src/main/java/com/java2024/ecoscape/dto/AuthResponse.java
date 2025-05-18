package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Role;
import com.java2024.ecoscape.models.UserStatus;

import java.time.LocalDate;
import java.util.Set;

public class AuthResponse {
    private String jwtToken;
    private Long userId;
    private String username;
    private Set<Role> roles;
    private String firstName;
    private String lastName;
    private String bio;
    private LocalDate birthDate;
    private String contactPhoneNumber;
    private String contactEmail;
    private String photoUrl;
    private UserStatus userStatus;

    public AuthResponse(String jwtToken, Long userId, String username, Set<Role> roles, String firstName, String lastName, String bio, LocalDate birthDate, String contactPhoneNumber, String contactEmail, String photoUrl, UserStatus userStatus) {
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.birthDate = birthDate;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
        this.photoUrl = photoUrl;
        this.userStatus = userStatus;
    }

    // Getters and setters for all fields

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
