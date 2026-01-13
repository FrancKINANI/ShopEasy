package com.ma.shopeasy.domain.model;

import java.util.List;

public class User {
    private String uid;
    private String email;
    private String name;
    private Address address;
    private List<CartItem> cart;

    public User() {
        // Required for Firebase
    }

    public User(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
    }

    // Getters and Setters
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public List<CartItem> getCart() { return cart; }
    public void setCart(List<CartItem> cart) { this.cart = cart; }

    public static class Address {
        private String street;
        private String city;
        private String zip;

        public Address() {}

        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getZip() { return zip; }
        public void setZip(String zip) { this.zip = zip; }
    }
}
