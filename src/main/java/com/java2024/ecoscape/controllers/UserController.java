package com.java2024.ecoscape.controllers;

import com.java2024.ecoscape.dto.UserRequest;
import com.java2024.ecoscape.dto.UserResponse;
import com.java2024.ecoscape.models.User;
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

    @GetMapping
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    //change to only admin once we publish
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    //change to only admin once we publish
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        User updatedUser = userService.updateUser(id, userRequest);

        UserResponse userUpdateResponse = new UserResponse(
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
}
