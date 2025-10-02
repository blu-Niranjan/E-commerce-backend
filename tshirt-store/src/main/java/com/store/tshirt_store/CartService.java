package com.store.tshirt_store;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final ShoppingCartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(ShoppingCartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // A helper method to get a user's cart by their username
    private ShoppingCart getOrCreateCartForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return cartRepository.findByUser(user).orElseGet(() -> {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    @Transactional
    public void addProductToCart(String username, Long productId) {
        // --- Checkpoint 1 ---
        System.out.println("--- ACTION: addProductToCart started for user: " + username + ", product ID: " + productId);

        try {
            ShoppingCart cart = getOrCreateCartForUser(username);
            // --- Checkpoint 2 ---
            System.out.println("--- INFO: Found or created cart with ID: " + cart.getId());

            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
            // --- Checkpoint 3 ---
            System.out.println("--- INFO: Found product: " + product.getName());

            Optional<CartItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            if (existingItem.isPresent()) {
                // --- Checkpoint 4a ---
                System.out.println("--- INFO: Item is already in cart. Incrementing quantity.");
                existingItem.get().incrementQuantity();
            } else {
                // --- Checkpoint 4b ---
                System.out.println("--- INFO: Item not in cart. Creating new CartItem.");
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProduct(product);
                newItem.setQuantity(1);
                cart.getItems().add(newItem);
            }

            cartRepository.save(cart);
            // --- Checkpoint 5 ---
            System.out.println("--- SUCCESS: Cart saved. Cart now has " + cart.getItems().size() + " unique item type(s).");

        } catch (Exception e) {
            // --- Checkpoint 6 (Error) ---
            System.err.println("--- ERROR: An exception occurred in addProductToCart: " + e.getMessage());
        }
    }

    @Transactional
    public void decrementProductInCart(String username, Long productId) {
        ShoppingCart cart = getOrCreateCartForUser(username);

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.decrementQuantity();
            if (item.getQuantity() <= 0) {
                cart.getItems().remove(item);
            }
            cartRepository.save(cart);
        }
    }

    public List<CartItem> getCartItems(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            Optional<ShoppingCart> cart = cartRepository.findByUser(user.get());
            return cart.map(ShoppingCart::getItems).orElse(Collections.emptyList());
        }
        return Collections.emptyList();
    }
}