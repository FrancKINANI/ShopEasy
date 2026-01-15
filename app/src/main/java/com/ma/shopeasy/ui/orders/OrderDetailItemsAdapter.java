package com.ma.shopeasy.ui.orders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ma.shopeasy.data.repository.ProductRepository;
import com.ma.shopeasy.databinding.ItemOrderDetailProductBinding;
import com.ma.shopeasy.domain.model.CartItem;

import java.util.Locale;

public class OrderDetailItemsAdapter extends ListAdapter<CartItem, OrderDetailItemsAdapter.ViewHolder> {

    private final ProductRepository productRepository;

    public OrderDetailItemsAdapter(ProductRepository productRepository) {
        super(DIFF_CALLBACK);
        this.productRepository = productRepository;
    }

    private static final DiffUtil.ItemCallback<CartItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<CartItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getProductId().equals(newItem.getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getQuantity() == newItem.getQuantity() && oldItem.getPrice() == newItem.getPrice();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderDetailProductBinding binding = ItemOrderDetailProductBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderDetailProductBinding binding;

        public ViewHolder(ItemOrderDetailProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CartItem item) {
            binding.tvProductName.setText(item.getProductName());
            binding.tvQuantityPrice
                    .setText(String.format(Locale.getDefault(), "%d x %.2f $", item.getQuantity(), item.getPrice()));
            binding.tvItemTotal
                    .setText(String.format(Locale.getDefault(), "%.2f $", item.getQuantity() * item.getPrice()));

            // Load product image if needed
            productRepository.getProductById(item.getProductId()).observeForever(product -> {
                if (product != null && product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
                    com.bumptech.glide.Glide.with(binding.ivProduct.getContext())
                            .load(product.getImageUrl())
                            .placeholder(com.ma.shopeasy.R.color.gray_100)
                            .into(binding.ivProduct);
                }
            });
        }
    }
}
