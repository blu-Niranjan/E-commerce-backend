package com.store.tshirt_store;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // <-- Add this import

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    // This is a magic method! Spring will find all orders for a given user.
    List<CustomerOrder> findByUser(User user);
}