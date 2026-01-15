package com.ma.shopeasy.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ma.shopeasy.databinding.FragmentManageQuestionsBinding;
import com.ma.shopeasy.ui.profile.HelpViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManageFAQFragment extends Fragment {

    private FragmentManageQuestionsBinding binding;
    private HelpViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentManageQuestionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HelpViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        binding.rvQuestions.setLayoutManager(new LinearLayoutManager(getContext()));

        ContactMessageAdapter adapter = new ContactMessageAdapter();
        binding.rvQuestions.setAdapter(adapter);

        viewModel.getContactMessages().observe(getViewLifecycleOwner(), resource -> {
            if (resource.status == com.ma.shopeasy.utils.Resource.Status.SUCCESS && resource.data != null) {
                adapter.submitList(resource.data);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
