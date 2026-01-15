package com.ma.shopeasy.ui.orders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ma.shopeasy.databinding.ItemOrderBinding;
import com.ma.shopeasy.domain.model.Order;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrdersAdapter extends ListAdapter<Order, OrdersAdapter.OrderViewHolder> {

    public OrdersAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Order> DIFF_CALLBACK = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getOrderId() != null && oldItem.getOrderId().equals(newItem.getOrderId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getStatus().equals(newItem.getStatus()) && oldItem.getTotal() == newItem.getTotal();
        }
    };

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderBinding binding;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        public OrderViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.tvOrderId
                    .setText("Order #" + (order.getOrderId() != null ? order.getOrderId().substring(0, 8) : "..."));
            binding.tvStatus.setText(order.getStatus().toUpperCase());
            binding.tvTotal.setText(String.format(Locale.getDefault(), "Total: $%.2f", order.getTotal()));
            if (order.getOrderDate() != null) {
                binding.tvDate.setText("Date: " + dateFormat.format(order.getOrderDate()));
            }
        }
    }
}
