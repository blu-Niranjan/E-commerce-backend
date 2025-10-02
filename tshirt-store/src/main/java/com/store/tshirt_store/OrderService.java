package com.store.tshirt_store;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final ShoppingCartRepository cartRepository;
    private final CustomerOrderRepository orderRepository;

    public OrderService(UserRepository userRepository, ShoppingCartRepository cartRepository, CustomerOrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public CustomerOrder placeOrder(String username, OrderRequest orderRequest) {
        // 1. Find the user and their cart
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShoppingCart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot place an order with an empty cart.");
        }

        // 2. Create a new order object
        CustomerOrder order = new CustomerOrder();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Placed");
        order.setShippingAddress(orderRequest.getShippingAddress());

        // 3. Convert cart items into order items and calculate total price
        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(order);
            orderItem.setProductId(cartItem.getProduct().getId());
            orderItem.setProductName(cartItem.getProduct().getName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        double totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);

        // 4. Save the completed order to the database
        CustomerOrder savedOrder = orderRepository.save(order);

        // 5. Clear the user's shopping cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }
    @Transactional
    public CustomerOrder cancelOrder(Long orderId) {
        // 1. Find the order in the database
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 2. Check the business rule: only "Placed" orders can be canceled
        if ("Placed".equals(order.getStatus())) {
            order.setStatus("Canceled");
            return orderRepository.save(order);
        } else {
            // If the order is already shipped or delivered, we can't cancel it.
            throw new RuntimeException("Order cannot be canceled as it has already been processed.");
        }
    }
}