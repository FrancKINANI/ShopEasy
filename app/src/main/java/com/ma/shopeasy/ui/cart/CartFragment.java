package com.ma.shopeasy.ui.cart;

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

import com.ma.shopeasy.R;
import com.ma.shopeasy.data.repository.ProductRepository;
import com.ma.shopeasy.databinding.FragmentCartBinding;
import com.ma.shopeasy.domain.model.CartItem;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private CartAdapter adapter;
    private List<CartItem> currentItems;

    @Inject
    ProductRepository productRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);

        adapter = new CartAdapter(productRepository, new CartAdapter.OnCartItemClickListener() {
            @Override
            public void onRemoveClick(String productId) {
                viewModel.removeFromCart(productId);
            }

            @Override
            public void onIncreaseClick(String productId, int currentQuantity) {
                viewModel.updateQuantity(productId, currentQuantity + 1);
            }

            @Override
            public void onDecreaseClick(String productId, int currentQuantity) {
                if (currentQuantity > 1) {
                    viewModel.updateQuantity(productId, currentQuantity - 1);
                } else {
                    viewModel.removeFromCart(productId);
                }
            }
        });
        binding.rvCart.setAdapter(adapter);

        viewModel.getCart().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    currentItems = resource.data;
                    adapter.submitList(currentItems);
                    updateTotal(currentItems);
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        binding.btnCheckout.setOnClickListener(v -> {
            if (currentItems != null && !currentItems.isEmpty()) {
                viewModel.checkout(currentItems, viewModel.calculateTotal(currentItems))
                        .observe(getViewLifecycleOwner(), resource -> {
                            if (resource.status == com.ma.shopeasy.utils.Resource.Status.SUCCESS) {
                                Toast.makeText(getContext(), "Order placed successfully!", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_ordersFragment);
                            } else if (resource.status == com.ma.shopeasy.utils.Resource.Status.ERROR) {
                                Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTotal(List<CartItem> items) {
        double total = viewModel.calculateTotal(items);
        binding.tvTotal.setText(String.format(Locale.getDefault(), "$%.2f", total));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
