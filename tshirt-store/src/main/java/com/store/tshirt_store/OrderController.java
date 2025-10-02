package com.store.tshirt_store;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository; // <-- Add this
    private final CustomerOrderRepository orderRepository; // <-- Add this

    // Update the constructor
    public OrderController(OrderService orderService, UserRepository userRepository, CustomerOrderRepository orderRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository; // <-- Add this
        this.orderRepository = orderRepository; // <-- Add this
    }

    @PostMapping("/{username}")
    public ResponseEntity<CustomerOrder> placeOrder(@PathVariable String username, @RequestBody OrderRequest orderRequest) {
        try {
            CustomerOrder order = orderService.placeOrder(username, orderRequest);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // --- NEW METHOD FOR ORDER HISTORY ---
    @GetMapping("/{username}")
    public ResponseEntity<List<CustomerOrder>> getOrderHistory(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(orderRepository.findByUser(user)))
                .orElse(ResponseEntity.ok(Collections.emptyList()));
    }
    // --- NEW METHOD FOR CANCELING AN ORDER ---
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<CustomerOrder> cancelOrder(@PathVariable Long orderId) {
        try {
            CustomerOrder canceledOrder = orderService.cancelOrder(orderId);
            return ResponseEntity.ok(canceledOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Or send back the error message
        }
    }
}