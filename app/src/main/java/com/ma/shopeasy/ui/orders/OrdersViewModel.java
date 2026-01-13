package com.ma.shopeasy.ui.orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ma.shopeasy.data.repository.OrderRepository;
import com.ma.shopeasy.domain.model.Order;
import com.ma.shopeasy.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrdersViewModel extends ViewModel {

    private final OrderRepository repository;

    @Inject
    public OrdersViewModel(OrderRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<Order>>> getOrders() {
        return repository.getOrders();
    }
}
