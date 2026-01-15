package com.ma.shopeasy.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.ma.shopeasy.R;
import com.ma.shopeasy.databinding.FragmentAdminDashboardBinding;

public class AdminDashboardFragment extends Fragment {

    private FragmentAdminDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cardProducts.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_adminDashboardFragment_to_manageProductsFragment));

        binding.cardOrders.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_adminDashboardFragment_to_manageOrdersFragment));

        binding.cardSupport.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_adminDashboardFragment_to_manageFAQFragment));

        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Navigation.findNavController(v).navigate(R.id.action_adminDashboardFragment_to_loginFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
