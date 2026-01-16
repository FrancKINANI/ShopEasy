package com.ma.shopeasy.domain.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Order {

    private String orderId;
    private String userId;
    private List<CartItem> items;
    private double total;
    @ServerTimestamp
    private Date orderDate;
    private String status;

    public Order() {
        // Required for Firebase
    }

    public Order(String orderId, String userId, List<CartItem> items, double total) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.status = "Pending"; // Default status
    }

    // Getters and Setters
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalItemCount() {
        if (items == null)
            return 0;
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }
}
