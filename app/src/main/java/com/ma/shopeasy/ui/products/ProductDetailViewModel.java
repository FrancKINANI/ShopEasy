package com.ma.shopeasy.ui.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ma.shopeasy.data.repository.CartRepository;
import com.ma.shopeasy.data.repository.ProductRepository;
import com.ma.shopeasy.data.repository.HelpRepository;
import com.ma.shopeasy.domain.model.CartItem;
import com.ma.shopeasy.domain.model.ContactMessage;
import com.ma.shopeasy.domain.model.Product;
import com.ma.shopeasy.utils.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductDetailViewModel extends ViewModel {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final HelpRepository helpRepository;

    @Inject
    public ProductDetailViewModel(ProductRepository productRepository, CartRepository cartRepository,
            HelpRepository helpRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.helpRepository = helpRepository;
    }

    public LiveData<Product> getProduct(String id) {
        return productRepository.getProductById(id);
    }

    public void addToCart(Product product, int quantity) {
        cartRepository.addToCart(
                new CartItem(product.getId(), product.getName(), product.getImageUrl(), quantity, product.getPrice()));
    }

    public LiveData<com.ma.shopeasy.utils.Resource<Void>> updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    public LiveData<Resource<Void>> submitQuestion(ContactMessage message) {
        return helpRepository.sendContactMessage(message);
    }
}
