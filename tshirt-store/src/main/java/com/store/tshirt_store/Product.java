package com.store.tshirt_store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String imageUrl;
    private String category; 

    public Product() {}

    public Product(String name, double price, String imageUrl, String category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Product(Long id, String name, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
}
