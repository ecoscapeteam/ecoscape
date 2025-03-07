package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.UserRequest;
import com.java2024.ecoscape.dto.HostRequestResponse;
import com.java2024.ecoscape.dto.UserResponse;
import com.java2024.ecoscape.dto.UserUpdateDTO;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.models.UserStatus;
import com.java2024.ecoscape.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/request/{id}")
    public ResponseEntity<?> requestHost(@PathVariable Long id) {
        userService.hostRequest(id);

        return ResponseEntity.ok("Your request to become a host has been successfully applied!");
    }

    @GetMapping
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    //change to only admin once we publish
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<User>> getPendingUsers() {
        List<User> users = userService.findUserByStatus(UserStatus.PENDING);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    //change to only admin once we publish
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getBio(),
                user.getUserStatus(),
                user.getPhotoUrl(),
                user.getBirthDate(),
                user.getContactPhoneNumber(),
                user.getContactEmail()
        );

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<HostRequestResponse> rejectHost(@PathVariable Long id) {
        User user = userService.rejectUserRequest(id);

        HostRequestResponse hostRequestResponse = new HostRequestResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getBio(),
                user.getUserStatus(),
                user.getPhotoUrl(),
                user.getBirthDate(),
                user.getContactPhoneNumber(),
                user.getContactEmail()
        );

        return ResponseEntity.ok(hostRequestResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        if(userService.existsByContactEmailAndIdNot(userRequest.getContactEmail(), id)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("That contact email already exists");
        }

        if(userService.existsByContactPhoneNumberAndIdNot(userRequest.getContactPhoneNumber(), id)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("That contact phone number already exists");
        }

        User updatedUser = userService.updateUser(id, userRequest);

        UserUpdateDTO userUpdateResponse = new UserUpdateDTO(
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getBio(),
                updatedUser.getPhotoUrl(),
                updatedUser.getBirthDate(),
                updatedUser.getContactPhoneNumber(),
                updatedUser.getContactEmail()
        );

        return ResponseEntity.ok(userUpdateResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok("User successfully deleted!");
    }
}
