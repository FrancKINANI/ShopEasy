package com.ma.shopeasy.ui.admin;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ma.shopeasy.databinding.ItemContactMessageBinding;
import com.ma.shopeasy.domain.model.ContactMessage;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ContactMessageAdapter extends ListAdapter<ContactMessage, ContactMessageAdapter.ViewHolder> {

    public ContactMessageAdapter() {
        super(new DiffUtil.ItemCallback<ContactMessage>() {
            @Override
            public boolean areItemsTheSame(@NonNull ContactMessage oldItem, @NonNull ContactMessage newItem) {
                return (oldItem.getId() != null && oldItem.getId().equals(newItem.getId()));
            }

            @Override
            public boolean areContentsTheSame(@NonNull ContactMessage oldItem, @NonNull ContactMessage newItem) {
                return oldItem.getMessage().equals(newItem.getMessage())
                        && oldItem.getSubject().equals(newItem.getSubject());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactMessageBinding binding = ItemContactMessageBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemContactMessageBinding binding;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault());

        public ViewHolder(ItemContactMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ContactMessage message) {
            binding.tvSubject.setText(message.getSubject());
            binding.tvEmail.setText(message.getUserEmail());
            binding.tvMessage.setText(message.getMessage());

            if (message.getTimestamp() != null) {
                binding.tvDate.setText(dateFormat.format(message.getTimestamp().toDate()));
            } else {
                binding.tvDate.setText("");
            }
        }
    }
}
