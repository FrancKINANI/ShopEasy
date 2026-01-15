package com.ma.shopeasy.ui.profile;

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

import com.ma.shopeasy.databinding.FragmentHelpBinding;
import com.ma.shopeasy.domain.model.ContactMessage;
import com.ma.shopeasy.utils.Resource;

import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HelpFragment extends Fragment {

    private FragmentHelpBinding binding;
    private HelpViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HelpViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        binding.btnSubmitHelp.setOnClickListener(v -> {
            String subject = binding.etSubject.getText().toString().trim();
            String messageText = binding.etMessage.getText().toString().trim();

            if (subject.isEmpty() || messageText.isEmpty()) {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            ContactMessage message = new ContactMessage(
                    "User_ID", // Placeholder, ideally from AuthViewModel
                    "user@example.com", // Placeholder
                    subject,
                    messageText);

            viewModel.sendContactMessage(message).observe(getViewLifecycleOwner(), resource -> {
                if (resource.status == Resource.Status.SUCCESS) {
                    Toast.makeText(getContext(), "Message envoyé. Merci !", Toast.LENGTH_LONG).show();
                    binding.etSubject.setText("");
                    binding.etMessage.setText("");
                } else if (resource.status == Resource.Status.ERROR) {
                    Toast.makeText(getContext(), "Erreur : " + resource.message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Setup FAQ recycler if needed (placeholder for fetching articles)
        // For now, it's empty as per the UI design
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}