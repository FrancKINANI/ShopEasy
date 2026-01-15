package com.ma.shopeasy.ui.cart;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ma.shopeasy.data.repository.ProductRepository;
import com.ma.shopeasy.databinding.ItemCartBinding;
import com.ma.shopeasy.domain.model.CartItem;

import java.util.Locale;

public class CartAdapter extends ListAdapter<CartItem, CartAdapter.CartViewHolder> {

    private final ProductRepository productRepository;
    private final OnCartItemClickListener listener;

    public interface OnCartItemClickListener {
        void onRemoveClick(String productId);

        void onIncreaseClick(String productId, int currentQuantity);

        void onDecreaseClick(String productId, int currentQuantity);
    }

    public CartAdapter(ProductRepository productRepository, OnCartItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.productRepository = productRepository;
        this.listener = listener;
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
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(getItem(position), productRepository, listener);
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private final ItemCartBinding binding;

        public CartViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CartItem item, ProductRepository repository, OnCartItemClickListener listener) {
            // Fetch product details for name and image
            repository.getProductById(item.getProductId()).observeForever(product -> {
                if (product != null) {
                    binding.tvName.setText(product.getName());
                    // In a real app, use Glide here to load image
                    /*
                     * Glide.with(binding.ivProduct.getContext())
                     * .load(product.getImageUrl())
                     * .placeholder(android.R.drawable.ic_menu_gallery)
                     * .into(binding.ivProduct);
                     */
                }
            });

            binding.tvPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));
            binding.tvQuantity.setText(String.valueOf(item.getQuantity()));

            binding.btnRemove.setOnClickListener(v -> listener.onRemoveClick(item.getProductId()));
            binding.btnPlus.setOnClickListener(v -> listener.onIncreaseClick(item.getProductId(), item.getQuantity()));
            binding.btnMinus.setOnClickListener(v -> listener.onDecreaseClick(item.getProductId(), item.getQuantity()));
        }
    }
}
