package com.ma.shopeasy.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ma.shopeasy.data.repository.ProductRepository;
import com.ma.shopeasy.domain.model.Product;
import com.ma.shopeasy.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final ProductRepository repository;
    public final LiveData<List<Product>> cachedProducts;

    @Inject
    public HomeViewModel(ProductRepository repository) {
        this.repository = repository;
        this.cachedProducts = repository.getLocalProducts();
    }

    public LiveData<Resource<List<Product>>> refreshProducts() {
        return repository.fetchRemoteProducts();
    }
}
