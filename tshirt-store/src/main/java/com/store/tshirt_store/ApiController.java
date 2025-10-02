package com.store.tshirt_store;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ApiController {

    private final ProductRepository productRepository; // Our new librarian

    // Tell Spring to give us the librarian when it creates the controller
    public ApiController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // We no longer need the "/hello" endpoint, so you can delete it if you want

    @GetMapping("/api/products")
    public List<Product> getAllProducts() {
        // Ask the librarian to find all the products in the database!
        return productRepository.findAll();
    }
    // This new method finds ONE product by its ID
    @GetMapping("/api/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        // Ask the librarian to find a product by its ID.
        // If it's not found, it will return null (nothing).
        return productRepository.findById(id).orElse(null);
    }
    // This new method SAVES a new product to the database
    @PostMapping("/api/products")
    public Product addProduct(@RequestBody Product product) {
        // Ask the librarian to save the new product and return it
        return productRepository.save(product);
    }
    // Inside ApiController.java
    @GetMapping("/api/products/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productRepository.findByCategory(category);
    }
}