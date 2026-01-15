package com.ma.shopeasy.ui.orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ma.shopeasy.data.repository.OrderRepository;
import com.ma.shopeasy.domain.model.Order;
import com.ma.shopeasy.utils.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrderDetailViewModel extends ViewModel {

    private final OrderRepository repository;

    @Inject
    public OrderDetailViewModel(OrderRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<Order>> getOrderById(String orderId) {
        return repository.getOrderById(orderId);
    }
}
