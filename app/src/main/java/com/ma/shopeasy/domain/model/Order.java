package com.ma.shopeasy.domain.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.List;

public class Order {
    private String orderId;
    private String userId;
    private List<CartItem> items;
    private double total;
    private String status; // "pending" | "shipped" | "delivered" | "cancelled"

    @ServerTimestamp
    private Timestamp timestamp;

    public Order() {
    }

    public Order(String orderId, String userId, List<CartItem> items, double total, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
