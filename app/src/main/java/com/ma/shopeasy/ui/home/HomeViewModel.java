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
    private final com.ma.shopeasy.data.repository.CartRepository cartRepository;
    public final LiveData<List<Product>> cachedProducts;

    @Inject
    public HomeViewModel(ProductRepository repository, com.ma.shopeasy.data.repository.CartRepository cartRepository) {
        this.repository = repository;
        this.cartRepository = cartRepository;
        this.cachedProducts = repository.getLocalProducts();
    }

    public LiveData<Resource<List<Product>>> refreshProducts() {
        return repository.fetchRemoteProducts();
    }

    public void addToCart(Product product) {
        cartRepository.addToCart(new com.ma.shopeasy.domain.model.CartItem(product.getId(), product.getName(),
                product.getImageUrl(), 1, product.getPrice()));
    }
}
