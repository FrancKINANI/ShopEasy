package com.ma.shopeasy.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ma.shopeasy.domain.model.Product;

@Database(entities = { Product.class }, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
