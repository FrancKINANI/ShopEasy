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
import com.ma.shopeasy.databinding.FragmentOrdersBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;
    private OrdersViewModel viewModel;
    private OrdersAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrdersViewModel.class);

        boolean isAdmin = getArguments() != null && getArguments().getBoolean("isAdmin", false);

        adapter = new OrdersAdapter(orderId -> {
            Bundle args = new Bundle();
            args.putString("orderId", orderId);
            args.putBoolean("isAdmin", isAdmin);
            int actionId = isAdmin ? R.id.action_manageOrdersFragment_to_orderDetailFragment
                    : R.id.action_ordersFragment_to_orderDetailFragment;
            Navigation.findNavController(view).navigate(actionId, args);
        });
        binding.rvOrders.setAdapter(adapter);

        if (isAdmin) {
            binding.tvTitle.setText("Toutes les Commandes");
        }

        (isAdmin ? viewModel.getAllOrders() : viewModel.getOrders()).observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.emptyState.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    if (resource.data == null || resource.data.isEmpty()) {
                        binding.emptyState.setVisibility(View.VISIBLE);
                    } else {
                        binding.emptyState.setVisibility(View.GONE);
                        adapter.submitList(resource.data);
                    }
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
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
