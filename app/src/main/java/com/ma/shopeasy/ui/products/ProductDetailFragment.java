package com.ma.shopeasy.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.ma.shopeasy.databinding.FragmentProductDetailBinding;
import com.ma.shopeasy.domain.model.Product;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetailFragment extends Fragment {

    private FragmentProductDetailBinding binding;
    private ProductDetailViewModel viewModel;
    private String productId;
    private int quantity = 1;
    private Product currentProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString("productId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        viewModel.getProduct(productId).observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                currentProduct = product;
                displayProduct(product);
            }
        });

        binding.btnPlus.setOnClickListener(v -> {
            quantity++;
            binding.tvQuantity.setText(String.valueOf(quantity));
        });

        binding.btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnAddToCart.setOnClickListener(v -> {
            if (currentProduct != null) {
                viewModel.addToCart(currentProduct, quantity);
                Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProduct(Product product) {
        binding.tvName.setText(product.getName());
        binding.tvPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        binding.tvDescription.setText(product.getDescription());
        binding.ratingBar.setRating(product.getRating());

        Glide.with(this)
                .load(product.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.ivProduct);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
