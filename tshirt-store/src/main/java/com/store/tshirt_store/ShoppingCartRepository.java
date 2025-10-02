package com.store.tshirt_store;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    // A magic method to find a cart by the user who owns it
    Optional<ShoppingCart> findByUser(User user);
}