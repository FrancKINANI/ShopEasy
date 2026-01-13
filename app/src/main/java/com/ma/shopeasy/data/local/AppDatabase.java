package com.ma.shopeasy.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ma.shopeasy.domain.model.Product;

@Database(entities = { Product.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
