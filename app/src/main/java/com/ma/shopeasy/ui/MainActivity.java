package com.ma.shopeasy.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.ma.shopeasy.R;
import com.ma.shopeasy.databinding.ActivityMainBinding;
import com.ma.shopeasy.domain.model.User;
import com.ma.shopeasy.ui.auth.AuthViewModel;
import com.ma.shopeasy.utils.Resource;
import com.ma.shopeasy.utils.SettingsManager;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Inject
    SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Listen for theme changes and apply them only if different
        settingsManager.isDarkMode()
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDark -> {
                    int targetMode = isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
                    if (AppCompatDelegate.getDefaultNightMode() != targetMode) {
                        AppCompatDelegate.setDefaultNightMode(targetMode);
                    }
                }, throwable -> android.util.Log.e("MainActivity", "Theme error", throwable));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        setupNavigation(navController);
    }

    private void setupNavigation(NavController navController) {
        NavigationUI.setupWithNavController(binding.bottomNav, navController);

        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getUserData().observe(this, resource -> {
            if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                binding.bottomNav.getMenu().clear();
                if (resource.data.getRole() == User.Role.ADMIN) {
                    binding.bottomNav.inflateMenu(R.menu.admin_bottom_nav_menu);
                } else {
                    binding.bottomNav.inflateMenu(R.menu.bottom_nav_menu);
                }
                NavigationUI.setupWithNavController(binding.bottomNav, navController);
            }
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            if (id == R.id.loginFragment || id == R.id.signupFragment || id == R.id.forgotPasswordFragment) {
                binding.bottomNav.setVisibility(View.GONE);
            } else {
                binding.bottomNav.setVisibility(View.VISIBLE);
            }
        });
    }
}
