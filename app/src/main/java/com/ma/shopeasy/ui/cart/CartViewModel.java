package com.ma.shopeasy.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.ma.shopeasy.data.repository.CartRepository;
import com.ma.shopeasy.data.repository.OrderRepository;
import com.ma.shopeasy.data.repository.ProductRepository;
import com.ma.shopeasy.domain.model.CartItem;
import com.ma.shopeasy.domain.model.Order;
import com.ma.shopeasy.domain.model.Product;
import com.ma.shopeasy.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CartViewModel extends ViewModel {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final FirebaseAuth auth;

    @Inject
    public CartViewModel(CartRepository cartRepository, OrderRepository orderRepository,
            ProductRepository productRepository, FirebaseAuth auth) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.auth = auth;
    }

    public LiveData<Resource<List<CartItem>>> getCart() {
        return cartRepository.getCart();
    }

    public LiveData<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public void removeFromCart(String productId) {
        cartRepository.removeFromCart(productId);
    }

    public void updateQuantity(String productId, int quantity) {
        cartRepository.updateQuantity(productId, quantity);
    }

    public LiveData<Resource<String>> checkout(List<CartItem> items, double total) {
        Order order = new Order(null, auth.getUid(), items, total, "pending");
        return orderRepository.placeOrder(order);
    }

    public double calculateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
