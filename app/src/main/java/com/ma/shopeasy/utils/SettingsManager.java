package com.ma.shopeasy.utils;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class SettingsManager {
    private final RxDataStore<Preferences> dataStore;
    private static final Preferences.Key<Boolean> IS_DARK_MODE = PreferencesKeys.booleanKey("is_dark_mode");

    public SettingsManager(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "settings").build();
    }

    public Flowable<Boolean> isDarkMode() {
        return dataStore.data().map(prefs -> prefs.get(IS_DARK_MODE) != null ? prefs.get(IS_DARK_MODE) : false);
    }

    public Single<Preferences> setDarkMode(boolean isDark) {
        return dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(IS_DARK_MODE, isDark);
            return Single.just(mutablePreferences);
        });
    }
}
