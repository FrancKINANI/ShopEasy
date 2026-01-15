package com.ma.shopeasy.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ma.shopeasy.R;
import com.ma.shopeasy.databinding.FragmentHomeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private ProductAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerView();
        observeViewModel();

        binding.swipeRefresh.setOnRefreshListener(this::refreshData);

        setupSearch();
        setupFilters();

        // Initial fetch
        refreshData();
    }

    private void setupSearch() {
        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void setupFilters() {
        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            filterList(binding.searchView.getQuery().toString());
        });
    }

    private void filterList(String query) {
        java.util.List<com.ma.shopeasy.domain.model.Product> products = viewModel.cachedProducts.getValue();
        if (products == null)
            return;

        java.util.List<com.ma.shopeasy.domain.model.Product> filtered = new java.util.ArrayList<>();
        for (com.ma.shopeasy.domain.model.Product p : products) {
            if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(p);
            }
        }

        // Sorting
        int checkedId = binding.chipGroup.getCheckedChipId();
        if (checkedId == R.id.chipPriceLow) {
            filtered.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
        } else if (checkedId == R.id.chipPriceHigh) {
            filtered.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
        }

        adapter.submitList(filtered);
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(com.ma.shopeasy.domain.model.Product product) {
                Bundle bundle = new Bundle();
                bundle.putString("productId", product.getId());
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_homeFragment_to_productDetailFragment, bundle);
            }

            @Override
            public void onAddToCartClick(com.ma.shopeasy.domain.model.Product product) {
                viewModel.addToCart(product);
                Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvProducts.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.cachedProducts.observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                filterList(binding.searchView.getQuery().toString());
            }
        });
    }

    private void refreshData() {
        viewModel.refreshProducts().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    binding.swipeRefresh.setRefreshing(true);
                    break;
                case SUCCESS:
                    binding.swipeRefresh.setRefreshing(false);
                    break;
                case ERROR:
                    binding.swipeRefresh.setRefreshing(false);
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
