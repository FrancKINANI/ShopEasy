package com.ma.shopeasy.ui.auth;

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
import com.ma.shopeasy.databinding.FragmentLoginBinding;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Login Fragment
 * ✅ Security: Input validation, secure credential handling
 */
@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private AuthViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // ✅ Check if already logged in
        if (viewModel.user.getValue() != null) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
            return;
        }

        setupLoginButton(view);
        setupSignupLink(view);
        setupForgotPasswordLink(view);
    }

    /**
     * Setup login button with validation
     * ✅ Security: Input validation before sending to server
     */
    private void setupLoginButton(View view) {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString();

            // ✅ Input validation
            if (!validateInputs(email, password)) {
                return;
            }

            // Disable button and show loading
            binding.btnLogin.setEnabled(false);
            binding.progressBar.setVisibility(View.VISIBLE);

            // Perform login
            viewModel.login(email, password).observe(getViewLifecycleOwner(), resource -> {
                handleLoginResponse(resource, view);
            });
        });
    }

    /**
     * Validate email and password
     * ✅ Security: Email format, password strength validation
     */
    private boolean validateInputs(String email, String password) {
        // Check if fields are empty
        if (email.isEmpty() || password.isEmpty()) {
            showError("Please fill all fields");
            return false;
        }

        // ✅ Validate email format
        if (!isValidEmail(email)) {
            showError("Please enter a valid email address");
            binding.etEmail.requestFocus();
            return false;
        }

        // ✅ Validate password (minimum length)
        if (password.length() < 6) {
            showError("Password must be at least 6 characters");
            binding.etPassword.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Handle login response
     */
    private void handleLoginResponse(com.ma.shopeasy.utils.Resource<?> resource, View view) {
        switch (resource.status) {
            case LOADING:
                binding.progressBar.setVisibility(View.VISIBLE);
                break;

            case SUCCESS:
                binding.progressBar.setVisibility(View.GONE);
                binding.btnLogin.setEnabled(true);
                showSuccess("Login successful");
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                break;

            case ERROR:
                binding.progressBar.setVisibility(View.GONE);
                binding.btnLogin.setEnabled(true);
                showError(resource.message != null ? resource.message : "Login failed");
                // Clear password for security
                binding.etPassword.setText("");
                break;
        }
    }

    /**
     * Setup forgot password link
     */
    private void setupForgotPasswordLink(View view) {
        binding.tvForgotPassword.setOnClickListener(v -> {
            // TODO: Navigate to forgot password screen
            // Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
            showInfo("Password reset feature coming soon");
        });
    }

    /**
     * Setup signup link
     */
    private void setupSignupLink(View view) {
        binding.tvToSignup.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment)
        );
    }

    // ✅ VALIDATION HELPERS

    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // ✅ UI HELPERS

    private void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showSuccess(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showInfo(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
