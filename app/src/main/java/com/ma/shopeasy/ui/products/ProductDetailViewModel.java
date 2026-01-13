package com.ma.shopeasy.ui.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ma.shopeasy.data.repository.CartRepository;
import com.ma.shopeasy.data.repository.ProductRepository;
import com.ma.shopeasy.domain.model.CartItem;
import com.ma.shopeasy.domain.model.Product;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductDetailViewModel extends ViewModel {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Inject
    public ProductDetailViewModel(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public LiveData<Product> getProduct(String id) {
        return productRepository.getProductById(id);
    }

    public void addToCart(Product product, int quantity) {
        cartRepository.addToCart(new CartItem(product.getId(), quantity, product.getPrice()));
    }
}
