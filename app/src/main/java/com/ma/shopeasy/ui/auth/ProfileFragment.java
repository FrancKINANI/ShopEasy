package com.ma.shopeasy.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseUser;
import com.ma.shopeasy.R;
import com.ma.shopeasy.databinding.FragmentProfileBinding;
import com.ma.shopeasy.utils.SettingsManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private AuthViewModel viewModel;

    @Inject
    SettingsManager settingsManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        viewModel.user.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.tvUserName.setText(user.getDisplayName() != null ? user.getDisplayName() : "User");
                binding.tvUserEmail.setText(user.getEmail());
            }
        });

        // Handle Dark Mode Switch
        settingsManager.isDarkMode()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDark -> {
                    binding.switchDarkMode.setChecked(isDark);
                });

        binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setDarkMode(isChecked).subscribe();
        });

        binding.btnLogout.setOnClickListener(v -> {
            viewModel.logout();
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
