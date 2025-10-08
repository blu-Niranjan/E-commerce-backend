package com.store.tshirt_store;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ApiController {

    private final ProductRepository productRepository;
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
