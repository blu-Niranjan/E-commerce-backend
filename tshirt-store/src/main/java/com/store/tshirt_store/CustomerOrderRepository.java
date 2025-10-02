package com.store.tshirt_store;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; 

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    
    List<CustomerOrder> findByUser(User user);
}
