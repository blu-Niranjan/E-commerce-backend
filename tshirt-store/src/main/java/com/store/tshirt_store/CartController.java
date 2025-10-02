package com.store.tshirt_store;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // GET /api/cart/{username} - View a specific user's cart
    @GetMapping("/{username}")
    public List<CartItem> getCart(@PathVariable String username) {
        return cartService.getCartItems(username);
    }

    // POST /api/cart/add/{username}/{productId} - Add a product to a user's cart
    @PostMapping("/add/{username}/{productId}")
    public void addToCart(@PathVariable String username, @PathVariable Long productId) {
        cartService.addProductToCart(username, productId);
    }

    // POST /api/cart/decrement/{username}/{productId} - Decrement from a user's cart
    @PostMapping("/decrement/{username}/{productId}")
    public void decrementFromCart(@PathVariable String username, @PathVariable Long productId) {
        cartService.decrementProductInCart(username, productId);
    }
}