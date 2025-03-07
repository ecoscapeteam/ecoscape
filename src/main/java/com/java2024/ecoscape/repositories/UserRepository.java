package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByContactEmailAndIdNot(String contactEmail, Long id);

    Optional<User> findByContactPhoneNumberAndIdNot(String contactPhoneNumber, Long id);
}
