package com.ma.shopeasy.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ma.shopeasy.domain.model.CartItem;
import com.ma.shopeasy.domain.model.Order;
import com.ma.shopeasy.domain.model.User;
import com.ma.shopeasy.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CartRepository {

    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;

    @Inject
    public CartRepository(FirebaseFirestore firestore, FirebaseAuth auth) {
        this.firestore = firestore;
        this.auth = auth;
    }

    public LiveData<Resource<List<CartItem>>> getCart() {
        MutableLiveData<Resource<List<CartItem>>> result = new MutableLiveData<>();
        String uid = auth.getUid();

        if (uid == null) {
            result.setValue(Resource.error("User not logged in"));
            return result;
        }

        result.setValue(Resource.loading());
        firestore.collection("users").document(uid)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        result.setValue(Resource.error(error.getMessage()));
                        return;
                    }
                    if (value != null && value.exists()) {
                        User user = value.toObject(User.class);
                        if (user != null && user.getCart() != null) {
                            result.setValue(Resource.success(user.getCart()));
                        } else {
                            result.setValue(Resource.success(new ArrayList<>()));
                        }
                    } else {
                        result.setValue(Resource.success(new ArrayList<>()));
                    }
                });

        return result;
    }

    public void addToCart(CartItem item) {
        String uid = auth.getUid();
        if (uid == null) {
            return;
        }

        // ✅ Validation
        if (!isValidProductId(item.getProductId())) {
            return;
        }

        DocumentReference userRef = firestore.collection("users").document(uid);
        firestore.runTransaction(transaction -> {
            User user = transaction.get(userRef).toObject(User.class);
            if (user == null) {
                String name = auth.getCurrentUser().getDisplayName();
                String email = auth.getCurrentUser().getEmail();
                if (name == null || name.trim().isEmpty()) {
                    name = email; // Fallback to email if name is not available
                }
                user = new User(uid, name, email, "", User.Role.USER);
            }
            List<CartItem> cart = user.getCart();
            if (cart == null)
                cart = new ArrayList<>();

            boolean found = false;
            for (CartItem ci : cart) {
                if (ci.getProductId().equals(item.getProductId())) {
                    ci.setQuantity(ci.getQuantity() + item.getQuantity());
                    found = true;
                    break;
                }
            }
            if (!found)
                cart.add(item);

            user.setCart(cart);
            transaction.set(userRef, user);
            return null;
        }).addOnFailureListener(e -> {
            // Log failure
        });
    }

    private boolean isValidProductId(String productId) {
        return productId != null && productId.matches("^[a-zA-Z0-9_-]+$");
    }

    public void removeFromCart(String productId) {
        String uid = auth.getUid();
        if (uid == null)
            return;

        DocumentReference userRef = firestore.collection("users").document(uid);
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            if (user != null && user.getCart() != null) {
                List<CartItem> cart = user.getCart();
                cart.removeIf(item -> item.getProductId().equals(productId));
                userRef.update("cart", cart);
            }
        });
    }

    public void updateQuantity(String productId, int quantity) {
        String uid = auth.getUid();
        if (uid == null)
            return;

        DocumentReference userRef = firestore.collection("users").document(uid);
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            if (user != null && user.getCart() != null) {
                List<CartItem> cart = user.getCart();
                for (CartItem item : cart) {
                    if (item.getProductId().equals(productId)) {
                        item.setQuantity(quantity);
                        break;
                    }
                }
                userRef.update("cart", cart);
            }
        });
    }

    public void clearCart() {
        String uid = auth.getUid();
        if (uid == null) {
            return;
        }
        DocumentReference userRef = firestore.collection("users").document(uid);
        userRef.update("cart", new ArrayList<>());
    }

    public LiveData<Resource<Void>> placeOrder(List<CartItem> items, double total) {
        MutableLiveData<Resource<Void>> result = new MutableLiveData<>();
        String uid = auth.getUid();

        if (uid == null) {
            result.setValue(Resource.error("User not logged in"));
            return result;
        }

        result.setValue(Resource.loading());

        DocumentReference orderRef = firestore.collection("orders").document();
        Order order = new Order(orderRef.getId(), uid, items, total);

        firestore.collection("orders").document(order.getOrderId()).set(order)
                .addOnSuccessListener(aVoid -> {
                    clearCart();
                    result.setValue(Resource.success(null));
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }
}
