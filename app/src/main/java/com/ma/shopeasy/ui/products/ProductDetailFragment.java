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

        binding.btnAskQuestion.setOnClickListener(v -> {
            showAskQuestionDialog();
        });
    }

    private void showAskQuestionDialog() {
        android.widget.EditText etQuestion = new android.widget.EditText(getContext());
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Poser une question")
                .setView(etQuestion)
                .setPositiveButton("Envoyer", (dialog, which) -> {
                    String question = etQuestion.getText().toString().trim();
                    if (!question.isEmpty()) {
                        submitQuestion(question);
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void submitQuestion(String question) {
        if (currentProduct == null)
            return;

        // ✅ 1. Add to local product FAQ (for display purposes)
        com.ma.shopeasy.domain.model.FAQItem newItem = new com.ma.shopeasy.domain.model.FAQItem(question, null);
        currentProduct.getFaqList().add(newItem);
        viewModel.updateProduct(currentProduct).observe(getViewLifecycleOwner(), resource -> {
            // Updated product locally
        });

        // ✅ 2. Send as Support Ticket for Admin visibility
        com.google.firebase.auth.FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance()
                .getCurrentUser();
        String userEmail = user != null ? user.getEmail() : "Anonymous";
        String userId = user != null ? user.getUid() : "anonymous";

        com.ma.shopeasy.domain.model.ContactMessage message = new com.ma.shopeasy.domain.model.ContactMessage(
                userId,
                userEmail,
                "Question Produit: " + currentProduct.getName(),
                question);
        message.setRelatedProductId(productId);

        viewModel.submitQuestion(message).observe(getViewLifecycleOwner(), resource -> {
            if (resource.status == com.ma.shopeasy.utils.Resource.Status.SUCCESS) {
                Toast.makeText(getContext(), "Question envoyée. L'administrateur vous répondra bientôt.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayProduct(Product product) {
        binding.tvName.setText(product.getName());
        binding.tvPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        binding.tvDescription.setText(product.getDescription());
        binding.ratingBar.setRating(product.getRating());

        // ✅ Stock Status
        String status = product.getStockStatus();
        if ("IN_STOCK".equals(status)) {
            binding.tvStockStatus.setText("En stock");
            binding.tvStockStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            binding.btnAddToCart.setEnabled(true);
        } else if ("DELAYED".equals(status)) {
            binding.tvStockStatus.setText("2-3 jours de délai");
            binding.tvStockStatus.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            binding.btnAddToCart.setEnabled(true);
        } else {
            binding.tvStockStatus.setText("Rupture de stock");
            binding.tvStockStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            binding.btnAddToCart.setEnabled(false);
        }

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
