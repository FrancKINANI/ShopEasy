package com.ma.shopeasy.ui.orders;

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
import com.ma.shopeasy.databinding.FragmentOrderDetailBinding;
import com.ma.shopeasy.domain.model.Order;
import com.ma.shopeasy.utils.Resource;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderDetailFragment extends Fragment {

    private FragmentOrderDetailBinding binding;
    private OrderDetailViewModel viewModel;
    private OrderDetailItemsAdapter adapter;

    @Inject
    ProductRepository productRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);

        adapter = new OrderDetailItemsAdapter(productRepository);
        binding.rvOrderItems.setAdapter(adapter);

        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        String orderId = getArguments() != null ? getArguments().getString("orderId") : null;
        if (orderId != null) {
            fetchOrderDetails(orderId);
        } else {
            Toast.makeText(getContext(), "ID de commande manquant", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigateUp();
        }
    }

    private void fetchOrderDetails(String orderId) {
        viewModel.getOrderById(orderId).observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    if (resource.data != null) {
                        displayOrder(resource.data);
                    }
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void displayOrder(Order order) {
        binding.tvOrderId.setText(
                "Commande #" + (order.getOrderId() != null ? order.getOrderId().substring(0, 8).toUpperCase() : "..."));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
        if (order.getOrderDate() != null) {
            binding.tvDate.setText("Date: " + dateFormat.format(order.getOrderDate()));
        }

        binding.tvStatus.setText(order.getStatus().toUpperCase());
        updateStatusBackground(order.getStatus());

        adapter.submitList(order.getItems());

        binding.tvSubtotal.setText(String.format(Locale.getDefault(), "%.2f $", order.getTotal()));
        binding.tvTotal.setText(String.format(Locale.getDefault(), "%.2f $", order.getTotal()));
    }

    private void updateStatusBackground(String status) {
        if (status == null)
            return;

        int bgRes;
        switch (status.toLowerCase()) {
            case "delivered":
                bgRes = R.drawable.shape_status_delivered;
                break;
            case "shipping":
                bgRes = R.drawable.shape_status_shipping;
                break;
            default:
                bgRes = R.drawable.shape_status_pending;
                break;
        }
        binding.tvStatus.setBackgroundResource(bgRes);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
