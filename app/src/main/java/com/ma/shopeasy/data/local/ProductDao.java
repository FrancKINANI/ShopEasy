package com.ma.shopeasy.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ma.shopeasy.domain.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE category = :category")
    LiveData<List<Product>> getProductsByCategory(String category);

    @Query("SELECT * FROM products WHERE id = :id")
    LiveData<Product> getProductById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProducts(List<Product> products);

    @Query("DELETE FROM products")
    void deleteAllProducts();
}
