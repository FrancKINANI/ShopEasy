package com.ma.shopeasy.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.ma.shopeasy.data.repository.AuthRepository;
import com.ma.shopeasy.utils.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Authentication ViewModel
 * ✅ Security: Null-safe operations, proper lifecycle management
 */
@HiltViewModel
public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthRepository repository;
    private final MutableLiveData<FirebaseUser> _user = new MutableLiveData<>();
    public final LiveData<FirebaseUser> user = _user;
    private final MutableLiveData<com.ma.shopeasy.domain.model.User> _userProfile = new MutableLiveData<>();
    public final LiveData<com.ma.shopeasy.domain.model.User> userProfile = _userProfile;

    @Inject
    public AuthViewModel(AuthRepository repository) {
        this.repository = repository;

        // ✅ Null-safe initialization
        com.google.firebase.auth.FirebaseUser currentUser = repository.getCurrentUser();
        if (currentUser != null) {
            _user.setValue(currentUser);
            fetchUserProfile(currentUser.getUid());
            Log.d(TAG, "User already authenticated: " + currentUser.getEmail());
        }
    }

    /**
     * Login with email and password
     * ✅ Returns Resource<FirebaseUser> for proper state management
     */
    public LiveData<Resource<FirebaseUser>> login(String email, String password) {
        return repository.login(email, password);
    }

    /**
     * Sign up with email, password, and name
     * ✅ Creates user profile in Firestore
     */
    public LiveData<Resource<FirebaseUser>> signup(String email, String password, String name) {
        return repository.register(email, password, name);
    }

    /**
     * Request password reset
     * ✅ Sends reset email to user
     */
    public LiveData<Resource<Void>> resetPassword(String email) {
        return repository.resetPassword(email);
    }

    public void fetchUserProfile(String uid) {
        repository.getUserData(uid).observeForever(resource -> {
            if (resource.status == Resource.Status.SUCCESS) {
                _userProfile.setValue(resource.data);
            }
        });
    }

    public LiveData<Resource<com.ma.shopeasy.domain.model.User>> getUserData() {
        com.google.firebase.auth.FirebaseUser user = repository.getCurrentUser();
        if (user != null) {
            return repository.getUserData(user.getUid());
        }
        MutableLiveData<Resource<com.ma.shopeasy.domain.model.User>> result = new MutableLiveData<>();
        result.setValue(Resource.error("User not logged in"));
        return result;
    }

    public LiveData<Resource<com.ma.shopeasy.domain.model.User>> getUserProfile(String uid) {
        return repository.getUserData(uid);
    }

    /**
     * Logout
     * ✅ Clears user state
     */
    public void logout() {
        repository.logout();
        _user.setValue(null);
        _userProfile.setValue(null);
        Log.d(TAG, "User logged out");
    }

    /**
     * Get current user state
     */
    public boolean isUserLoggedIn() {
        return _user.getValue() != null;
    }
}
