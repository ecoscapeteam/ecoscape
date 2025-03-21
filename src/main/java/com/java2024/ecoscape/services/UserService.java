package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.UserRequest;
import com.java2024.ecoscape.dto.UserResponse;
import com.java2024.ecoscape.models.Role;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.models.UserStatus;
import com.java2024.ecoscape.repositories.ListingRepository;
import com.java2024.ecoscape.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ListingRepository listingRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ListingRepository listingRepository, AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.listingRepository = listingRepository;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        }

        userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public List<User> findUserByStatus(UserStatus userStatus) {
        return userRepository.findUserByUserStatus(UserStatus.PENDING);
    }

    public boolean existsByContactEmailAndIdNot(String contactEmail, Long id) {
        return userRepository.findByContactEmailAndIdNot(contactEmail, id).isPresent();
    }

    public boolean existsByContactPhoneNumberAndIdNot(String contactPhoneNumber, Long id) {
        return userRepository.findByContactPhoneNumberAndIdNot(contactPhoneNumber, id).isPresent();
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void hostRequest () {
        User authenticateUser = authenticationService.authenticateMethods();

        if (authenticateUser.getFirstName() == null || authenticateUser.getFirstName().isEmpty()
                || authenticateUser.getLastName() == null || authenticateUser.getLastName().isEmpty()
                || authenticateUser.getContactPhoneNumber() == null || authenticateUser.getContactPhoneNumber().isEmpty()
                || authenticateUser.getPhotoUrl() == null || authenticateUser.getPhotoUrl().isEmpty()
                || authenticateUser.getContactEmail() == null || authenticateUser.getContactEmail().isEmpty()
                || authenticateUser.getBirthDate() == null) {
            throw new IllegalArgumentException("You need to have a filled out user profile in order to put in a request for a host role.");
        }

        authenticateUser.setUserStatus(UserStatus.PENDING);

        userRepository.save(authenticateUser);
    }

    public List<UserResponse> findAllUsers() {
        User authenticateUser = authenticationService.authenticateMethods();

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(allusers -> new UserResponse(
                        allusers.getId(),
                        allusers.getUsername(),
                        allusers.getFirstName(),
                        allusers.getLastName(),
                        allusers.getBio(),
                        allusers.getUserStatus(),
                        allusers.getPhotoUrl(),
                        allusers.getBirthDate(),
                        allusers.getContactPhoneNumber(),
                        allusers.getContactEmail()))
                .collect(Collectors.toList());
    }

    public User findUserById(Long id) {
        User authenticateUser = authenticationService.authenticateMethods();

        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public User rejectHostRequest(Long id) {
        User authenticateUser = authenticationService.authenticateMethods();

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (existingUser.getUserStatus() == UserStatus.REJECTED) {
            throw new IllegalArgumentException("That user has already been rejected, they'll have to do a new request.");
        }

        if (existingUser.getUserStatus() == UserStatus.APPROVED) {
            throw new IllegalArgumentException("You can't reject an already approved user.");
        }

        if (existingUser.getUserStatus() != UserStatus.PENDING) {
            throw new IllegalArgumentException("The user has not requested the ability to become a host.");
        }

        existingUser.setUserStatus(UserStatus.REJECTED);

        return userRepository.save(existingUser);
    }

    public User approveHostRequest(Long id) {
        User authenticateUser = authenticationService.authenticateMethods();

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (existingUser.getUserStatus() == UserStatus.REJECTED) {
            throw new IllegalArgumentException("That user has already been rejected, they'll have to do a new request.");
        }

        if (existingUser.getUserStatus() == UserStatus.APPROVED) {
            throw new IllegalArgumentException("That user has already been approved.");
        }

        if (existingUser.getUserStatus() != UserStatus.PENDING) {
            throw new IllegalArgumentException("The user has not requested the ability to become a host.");
        }

        existingUser.setUserStatus(UserStatus.APPROVED);

        existingUser.getRoles().add(Role.HOST);
        existingUser.getRoles().remove(Role.USER);

        return userRepository.save(existingUser);
    }

    @Transactional
    public User updateUser(UserRequest userRequest) {
        User authenticateUser = authenticationService.authenticateMethods();

        authenticateUser.setFirstName(userRequest.getFirstName());
        authenticateUser.setLastName(userRequest.getLastName());
        authenticateUser.setBio(userRequest.getBio());
        authenticateUser.setPhotoUrl(userRequest.getPhotoUrl());
        authenticateUser.setBirthDate(userRequest.getBirthDate());
        authenticateUser.setContactPhoneNumber(userRequest.getContactPhoneNumber());
        authenticateUser.setContactEmail(userRequest.getContactEmail());

        return userRepository.save(authenticateUser);
    }

    public void deleteUser(Long id) {
        User authenticateUser = authenticationService.authenticateMethods();

        boolean isAdmin = authenticateUser.getRoles().stream().anyMatch(role -> role == Role.ADMIN);

        if(!isAdmin && !authenticateUser.getId().equals(id)) {
            throw new IllegalArgumentException("You can only delete your own account.");
        }

        if (listingRepository.existsByUserId(id)) {
            throw new IllegalArgumentException("You cannot delete this account with an existing listing, delete the listing first.");
        }

        userRepository.deleteById(id);
    }
}