package com.store.tshirt_store;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // <-- Make sure this is imported

public interface ProductRepository extends JpaRepository<Product, Long> {
    // This is magic! Spring will create the code to find by category for us.
    List<Product> findByCategory(String category);
}