package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.UserRequest;
import com.java2024.ecoscape.dto.UserResponse;
import com.java2024.ecoscape.models.Role;
import com.java2024.ecoscape.models.User;
import com.java2024.ecoscape.models.UserStatus;
import com.java2024.ecoscape.repositories.ListingRepository;
import com.java2024.ecoscape.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ListingRepository listingRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ListingRepository listingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.listingRepository = listingRepository;
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

    public void hostRequest (Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (existingUser.getFirstName() == null || existingUser.getFirstName().isEmpty()
                || existingUser.getLastName() == null || existingUser.getLastName().isEmpty()
                || existingUser.getContactPhoneNumber() == null || existingUser.getContactPhoneNumber().isEmpty()
                || existingUser.getPhotoUrl() == null || existingUser.getPhotoUrl().isEmpty()
                || existingUser.getContactEmail() == null || existingUser.getContactEmail().isEmpty()) {
            throw new IllegalArgumentException("You need to have a filled out user profile in order to put in a request for a host role.");
        }

        if (existingUser.getBirthDate() == null) {
            throw new IllegalArgumentException("You need to have a filled out user profile in order to put in a request for a host role.");
        }

        existingUser.setUserStatus(UserStatus.PENDING);

        userRepository.save(existingUser);
    }

    public List<UserResponse> findAllUsers() {
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));*/

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
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public User rejectHostRequest(Long id) {
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

    public User updateUser(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setBio(userRequest.getBio());
        existingUser.setPhotoUrl(userRequest.getPhotoUrl());
        existingUser.setBirthDate(userRequest.getBirthDate());
        existingUser.setContactPhoneNumber(userRequest.getContactPhoneNumber());
        existingUser.setContactEmail(userRequest.getContactEmail());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (listingRepository.existsByUserId(id)) {
            throw new IllegalArgumentException("You cannot delete your account with an existing listing, delete the listing first.");
        }

        userRepository.deleteById(id);
    }
}