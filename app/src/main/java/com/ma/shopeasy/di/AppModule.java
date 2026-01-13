package com.ma.shopeasy.di;

import android.content.Context;

import androidx.room.Room;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.ma.shopeasy.data.local.AppDatabase;
import com.ma.shopeasy.data.local.ProductDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public static FirebaseFirestore provideFirestore() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);
        return firestore;
    }

    @Provides
    @Singleton
    public static FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Provides
    @Singleton
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "shopeasy_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public static ProductDao provideProductDao(AppDatabase database) {
        return database.productDao();
    }

    @Provides
    @Singleton
    public static com.ma.shopeasy.utils.SettingsManager provideSettingsManager(@ApplicationContext Context context) {
        return new com.ma.shopeasy.utils.SettingsManager(context);
    }
}
