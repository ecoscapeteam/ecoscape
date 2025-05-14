package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Role;

import java.util.Set;

public class AuthResponse {
    private String jwtToken;
    private String username;
    private Long userId;
    private Set<Role> roles;

    public AuthResponse(String jwtToken, String username, Long userId, Set<Role> roles) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.userId = userId;   // ADD TO RESPONSE
        this.roles = roles;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
