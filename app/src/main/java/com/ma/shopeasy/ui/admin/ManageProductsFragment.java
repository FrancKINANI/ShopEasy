package com.ma.shopeasy.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ma.shopeasy.R;
import com.ma.shopeasy.databinding.FragmentManageProductsBinding;
import com.ma.shopeasy.ui.products.ProductDetailViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManageProductsFragment extends Fragment {

    private FragmentManageProductsBinding binding;
    private ProductDetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        binding = FragmentManageProductsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        com.ma.shopeasy.ui.home.HomeViewModel homeViewModel = new ViewModelProvider(this)
                .get(com.ma.shopeasy.ui.home.HomeViewModel.class);

        com.ma.shopeasy.ui.home.ProductAdapter adapter = new com.ma.shopeasy.ui.home.ProductAdapter(
                new com.ma.shopeasy.ui.home.ProductAdapter.OnProductClickListener() {
                    @Override
                    public void onProductClick(com.ma.shopeasy.domain.model.Product product) {
                        Bundle bundle = new Bundle();
                        bundle.putString("productId", product.getId());
                        androidx.navigation.Navigation.findNavController(view)
                                .navigate(R.id.action_manageProductsFragment_to_editProductAdminFragment, bundle);
                    }

                    @Override
                    public void onAddToCartClick(com.ma.shopeasy.domain.model.Product product) {
                        // Not needed in admin
                    }
                });

        binding.rvAdminProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvAdminProducts.setAdapter(adapter);

        homeViewModel.cachedProducts.observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                adapter.submitList(products);
            }
        });

        // Trigger fetch if empty or needed, but HomeFragment usually does it.
        // We can force refresh anyway
        homeViewModel.refreshProducts();

        binding.fabAddProduct.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(view)
                    .navigate(R.id.action_manageProductsFragment_to_editProductAdminFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
