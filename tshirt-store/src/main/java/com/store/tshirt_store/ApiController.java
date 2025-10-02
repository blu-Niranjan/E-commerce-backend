package com.store.tshirt_store;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ApiController {

    private final ProductRepository productRepository;

    // Tell Spring to give us the librarian when it creates the controller
    public ApiController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    

    @GetMapping("/api/products")
    public List<Product> getAllProducts() {
        // Ask the librarian to find all the products in the database!
        return productRepository.findAll();
    }
    
    @GetMapping("/api/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        // Ask the librarian to find a product by its ID.
        // If it's not found, it will return null (nothing).
        return productRepository.findById(id).orElse(null);
    }
    
    @PostMapping("/api/products")
    public Product addProduct(@RequestBody Product product) {
        // Ask the librarian to save the new product and return it
        return productRepository.save(product);
    }
    
    @GetMapping("/api/products/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productRepository.findByCategory(category);
    }
}
