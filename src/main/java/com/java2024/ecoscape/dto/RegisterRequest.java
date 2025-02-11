package com.java2024.ecoscape.dto;

import com.java2024.ecoscape.models.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Set<Role> roles;

    public RegisterRequest(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
