package com.ma.shopeasy.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ma.shopeasy.utils.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthRepository {

    private final FirebaseAuth firebaseAuth;

    @Inject
    public AuthRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public LiveData<Resource<FirebaseUser>> login(String email, String password) {
        MutableLiveData<Resource<FirebaseUser>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.setValue(Resource.success(firebaseAuth.getCurrentUser()));
                    } else {
                        result.setValue(Resource.error(
                                task.getException() != null ? task.getException().getMessage() : "Login failed"));
                    }
                });

        return result;
    }

    public LiveData<Resource<FirebaseUser>> register(String email, String password, String name) {
        MutableLiveData<Resource<FirebaseUser>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // In a real app, you'd also save the user name to Firestore here
                        result.setValue(Resource.success(firebaseAuth.getCurrentUser()));
                    } else {
                        result.setValue(Resource.error(task.getException() != null ? task.getException().getMessage()
                                : "Registration failed"));
                    }
                });

        return result;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}
