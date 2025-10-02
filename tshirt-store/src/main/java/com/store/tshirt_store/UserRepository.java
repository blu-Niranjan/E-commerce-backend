package com.store.tshirt_store;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // <-- Add this import

public interface UserRepository extends JpaRepository<User, Long> {

    // This is a magic method! Spring Data JPA will automatically create the code
    // to search for a user by their username.
    Optional<User> findByUsername(String username);
}