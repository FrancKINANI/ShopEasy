package com.ma.shopeasy.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

/**
 * Product Model
 * ✅ Security: Input validation in setters
 */
@Entity(tableName = "products")
public class Product implements Serializable {
    
    @PrimaryKey
    @NonNull
    private String id;
    
    private String name;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private float rating;
    private int stock;

    public Product() {
        // Required for Firebase/Room
    }

    public Product(@NonNull String id, String name, String description, double price, 
                   String category, String imageUrl, float rating, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.stock = stock;
    }

    // ✅ GETTERS & SETTERS WITH VALIDATION

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        if (id == null || !id.matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException("Invalid product ID format");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() > 500) {
            throw new IllegalArgumentException("Name too long (max 500 characters)");
        }
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 5000) {
            throw new IllegalArgumentException("Description too long (max 5000 characters)");
        }
        this.description = description != null ? description.trim() : null;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        // ✅ Round to 2 decimal places to avoid floating point issues
        this.price = Math.round(price * 100.0) / 100.0;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category != null && !category.matches("^[a-zA-Z0-9 &-]+$")) {
            throw new IllegalArgumentException("Invalid category format");
        }
        this.category = category != null ? category.trim() : null;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null) {
            // ✅ Basic URL validation
            if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
                throw new IllegalArgumentException("Invalid image URL (must be HTTP/HTTPS)");
            }
            if (imageUrl.length() > 2048) {
                throw new IllegalArgumentException("Image URL too long");
            }
        }
        this.imageUrl = imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        this.rating = rating;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = stock;
    }

    // ✅ EQUALS & HASHCODE

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Float.compare(product.rating, rating) == 0 &&
                stock == product.stock &&
                id.equals(product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(category, product.category) &&
                Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, category, imageUrl, rating, stock);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", rating=" + rating +
                '}';
    }
}
