package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.AuthRequest;
import com.java2024.ecoscape.dto.AuthResponse;
import com.java2024.ecoscape.dto.RegisterRequest;
import com.java2024.ecoscape.dto.RegisterResponse;
import com.java2024.ecoscape.models.Role;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.services.UserService;
import com.java2024.ecoscape.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if(userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());

        if(registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        } else {
            user.setRoles(registerRequest.getRoles());
        }

        userService.registerUser(user);

        RegisterResponse response = new RegisterResponse(
                "User registered successfully",
                user.getUsername(),
                user.getRoles()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);



            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findUserByUsername(userDetails.getUsername());

            String jwt = jwtUtil.generateToken(userDetails);



            AuthResponse response = new AuthResponse(
                    "Login successful!",
                    user.getId(),
                    user.getUsername(),
                    user.getRoles(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getBio(),
                    user.getBirthDate(),
                    user.getContactPhoneNumber(),
                    user.getContactEmail(),
                    user.getPhotoUrl(),
                    user.getUserStatus()
            );

            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(10 * 60 * 60)
                    .sameSite("Strict")
                    .build();


            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(response);

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect username or password");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) //CHANGE TO TRUE WHEN PUSHING TO PRODUCTION
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("You've logged out.");
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated!");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(
                "Authenticated",
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                user.getFirstName(),
                user.getLastName(),
                user.getBio(),
                user.getBirthDate(), // LocalDate
                user.getContactPhoneNumber(),
                user.getContactEmail(),
                user.getPhotoUrl(),
                user.getUserStatus()
        ));
    }
}
