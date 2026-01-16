package com.ma.shopeasy.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ma.shopeasy.domain.model.Order;
import com.ma.shopeasy.utils.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OrderRepository {

    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;

    @Inject
    public OrderRepository(FirebaseFirestore firestore, FirebaseAuth auth) {
        this.firestore = firestore;
        this.auth = auth;
    }

    public LiveData<Resource<Void>> placeOrder(Order order) {
        MutableLiveData<Resource<Void>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("orders").document(order.getOrderId())
                .set(order)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(null)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    public LiveData<Resource<List<Order>>> getOrders() {
        MutableLiveData<Resource<List<Order>>> result = new MutableLiveData<>();
        String uid = auth.getUid();

        if (uid == null) {
            result.setValue(Resource.error("User not logged in"));
            return result;
        }

        result.setValue(Resource.loading());
        firestore.collection("orders")
                .whereEqualTo("userId", uid)
                .orderBy("orderDate", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        result.setValue(Resource.error(error.getMessage()));
                        return;
                    }
                    if (value != null) {
                        result.setValue(Resource.success(value.toObjects(Order.class)));
                    }
                });
        return result;
    }

    public LiveData<Resource<List<Order>>> getAllOrders() {
        MutableLiveData<Resource<List<Order>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        firestore.collection("orders")
                .orderBy("orderDate", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        result.setValue(Resource.error(error.getMessage()));
                        return;
                    }
                    if (value != null) {
                        result.setValue(Resource.success(value.toObjects(Order.class)));
                    }
                });
        return result;
    }

    public LiveData<Resource<Order>> getOrderById(String orderId) {
        MutableLiveData<Resource<Order>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("orders").document(orderId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        result.setValue(Resource.error(error.getMessage()));
                        return;
                    }
                    if (value != null && value.exists()) {
                        result.setValue(Resource.success(value.toObject(Order.class)));
                    } else {
                        result.setValue(Resource.error("Order not found"));
                    }
                });

        return result;
    }

    public LiveData<Resource<Void>> updateOrderStatus(String orderId, String newStatus) {
        MutableLiveData<Resource<Void>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("orders").document(orderId)
                .update("status", newStatus)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(null)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }
}
