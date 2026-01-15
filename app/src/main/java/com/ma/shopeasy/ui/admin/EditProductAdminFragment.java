package com.ma.shopeasy.ui.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ma.shopeasy.R;
import com.ma.shopeasy.databinding.FragmentEditProductAdminBinding;
import com.ma.shopeasy.domain.model.Product;
import com.ma.shopeasy.ui.products.ProductDetailViewModel;
import com.ma.shopeasy.utils.Resource;

import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditProductAdminFragment extends Fragment {

    private FragmentEditProductAdminBinding binding;
    private ProductDetailViewModel viewModel;
    private String productId; // If null, we are adding new product

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProductAdminBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        if (getArguments() != null) {
            productId = getArguments().getString("productId");
            if (productId != null) {
                loadProduct(productId);
            }
        }

        setupUI();
    }

    private void setupUI() {
        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(getView()).navigateUp());

        // Setup Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.stock_status_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStockStatus.setAdapter(adapter);

        binding.btnSave.setOnClickListener(v -> saveProduct());
    }

    private void loadProduct(String id) {
        viewModel.getProduct(id).observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                binding.etName.setText(product.getName());
                binding.etPrice.setText(String.valueOf(product.getPrice()));
                binding.etStock.setText(String.valueOf(product.getStock()));
                binding.etSupplier.setText(product.getSupplierInfo());
                binding.etDescription.setText(product.getDescription());

                // Set Spinner selection based on product.stockStatus
                // (Simplified for now)
            }
        });
    }

    private void saveProduct() {
        String name = binding.etName.getText().toString().trim();
        String priceStr = binding.etPrice.getText().toString().trim();
        String stockStr = binding.etStock.getText().toString().trim();
        String supplier = binding.etSupplier.getText().toString().trim();
        String description = binding.etDescription.getText().toString().trim();
        String stockStatus = binding.spinnerStockStatus.getSelectedItem().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(getContext(), "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        int stock = stockStr.isEmpty() ? 0 : Integer.parseInt(stockStr);

        String id = productId != null ? productId : UUID.randomUUID().toString();
        // Ensure ID is alphanumeric/clean if needed, but UUID is fine usually.
        // Product model regex: "^[a-zA-Z0-9_-]+$" - UUID has dashes, so it fits.

        Product product = new Product(id, name, description, price, "General", "https://via.placeholder.com/150", 0, stock, supplier, stockStatus);

        viewModel.updateProduct(product).observe(getViewLifecycleOwner(), resource -> {
            if (resource.status == Resource.Status.SUCCESS) {
                Toast.makeText(getContext(), "Produit enregistré", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigateUp();
            } else if (resource.status == Resource.Status.ERROR) {
                Toast.makeText(getContext(), "Erreur: " + resource.message, Toast.LENGTH_SHORT).show();
            } else {
                // Loading...
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
