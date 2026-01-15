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

import com.ma.shopeasy.R;
import com.ma.shopeasy.databinding.FragmentProfileBinding;
import com.ma.shopeasy.utils.Resource;
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

        viewModel.getUserData().observe(getViewLifecycleOwner(), resource -> {
            if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                binding.tvUserName.setText(resource.data.getName());
                binding.tvUserEmail.setText(resource.data.getEmail());
            } else if (resource.status == Resource.Status.LOADING) {
                binding.tvUserName.setText("Chargement...");
            }
        });

        // Handle Dark Mode Switch - Avoid feedback loops
        settingsManager.isDarkMode()
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDark -> {
                    binding.switchDarkMode.setOnCheckedChangeListener(null);
                    binding.switchDarkMode.setChecked(isDark);
                    setupDarkModeListener();
                }, throwable -> android.util.Log.e("ProfileFragment", "Theme load error", throwable));

        binding.btnLogout.setOnClickListener(v -> {
            viewModel.logout();
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });

        binding.helpSupport.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_helpFragment);
        });

        binding.about.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_aboutFragment);
        });

        binding.btnEditProfile.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_editProfileFragment);
        });
    }

    private void setupDarkModeListener() {
        binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setDarkMode(isChecked).subscribe();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
