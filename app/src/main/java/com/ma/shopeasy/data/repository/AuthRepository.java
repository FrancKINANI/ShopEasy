package com.ma.shopeasy.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ma.shopeasy.domain.model.User;
import com.ma.shopeasy.utils.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthRepository {

    private static final String TAG = "AuthRepository";
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore;

    @Inject
    public AuthRepository(FirebaseAuth firebaseAuth, FirebaseFirestore firestore) {
        this.firebaseAuth = firebaseAuth;
        this.firestore = firestore;
    }

    /**
     * Login with email and password
     * ✅ Security: Input validation, secure error messaging
     */
    public LiveData<Resource<FirebaseUser>> login(String email, String password) {
        MutableLiveData<Resource<FirebaseUser>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        // ✅ Input validation
        if (!isValidEmail(email)) {
            result.setValue(Resource.error("Invalid email format"));
            return result;
        }
        if (!isValidPassword(password)) {
            result.setValue(Resource.error("Invalid password format"));
            return result;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            result.setValue(Resource.success(user));
                        } else {
                            result.setValue(Resource.error("Authentication failed"));
                        }
                    } else {
                        Exception exception = task.getException();
                        String userMessage = mapFirebaseExceptionToUserMessage(exception);
                        result.setValue(Resource.error(userMessage));
                        Log.e(TAG, "Login failed", exception);
                    }
                });

        return result;
    }

    /**
     * Register with email, password, and name
     * ✅ Creates user document in Firestore
     * ✅ Security: Input validation, secure error messaging
     */
    public LiveData<Resource<FirebaseUser>> register(String email, String password, String name) {
        MutableLiveData<Resource<FirebaseUser>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        // ✅ Input validation
        if (!isValidEmail(email)) {
            result.setValue(Resource.error("Invalid email format"));
            return result;
        }
        if (!isValidPassword(password)) {
            result.setValue(Resource.error("Password must be at least 8 characters with uppercase and numbers"));
            return result;
        }
        if (name == null || name.trim().isEmpty() || name.length() > 100) {
            result.setValue(Resource.error("Name must be between 1 and 100 characters"));
            return result;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // ✅ Update user profile name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates).addOnCompleteListener(profileTask -> {
                                if (profileTask.isSuccessful()) {
                                    // ✅ Create user document in Firestore
                                    createUserDocument(user, email, name, result);
                                } else {
                                    result.setValue(Resource.error("Failed to update profile"));
                                }
                            });
                        }
                    } else {
                        Exception exception = task.getException();
                        String userMessage = mapFirebaseExceptionToUserMessage(exception);
                        result.setValue(Resource.error(userMessage));
                        Log.e(TAG, "Registration failed", exception);
                    }
                });

        return result;
    }

    /**
     * Password Reset
     * ✅ Sends password reset email
     */
    public LiveData<Resource<Void>> resetPassword(String email) {
        MutableLiveData<Resource<Void>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        // ✅ Input validation
        if (!isValidEmail(email)) {
            result.setValue(Resource.error("Invalid email format"));
            return result;
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.setValue(Resource.success(null));
                    } else {
                        Exception exception = task.getException();
                        String userMessage = exception instanceof FirebaseAuthInvalidUserException
                                ? "Email not found"
                                : "Failed to send reset email";
                        result.setValue(Resource.error(userMessage));
                        Log.e(TAG, "Password reset failed", exception);
                    }
                });

        return result;
    }

    /**
     * Create user document in Firestore
     * ✅ Called after registration
     */
    private void createUserDocument(FirebaseUser user, String email, String name,
                                    MutableLiveData<Resource<FirebaseUser>> result) {
        User userData = new User(user.getUid(), email, name);

        firestore.collection("users")
                .document(user.getUid())
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    result.setValue(Resource.success(user));
                    Log.d(TAG, "User document created successfully");
                })
                .addOnFailureListener(e -> {
                    result.setValue(Resource.error("Failed to create user profile"));
                    Log.e(TAG, "Failed to create user document", e);
                });
    }

    /**
     * Get current authenticated user
     * ✅ Null-safe
     */
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    /**
     * Logout
     */
    public void logout() {
        firebaseAuth.signOut();
    }

    // ✅ VALIDATION METHODS

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean isValidPassword(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") && // At least one uppercase
                password.matches(".*\\d.*");     // At least one digit
    }

    // ✅ SECURITY: Map Firebase exceptions to safe user messages
    private String mapFirebaseExceptionToUserMessage(Exception exception) {
        if (exception == null) {
            return "Authentication failed. Please try again.";
        }

        if (exception instanceof FirebaseAuthInvalidUserException) {
            return "User account not found.";
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            return "Invalid email or password.";
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            return "Email already registered.";
        } else {
            Log.e(TAG, "Firebase authentication exception", exception);
            return "Authentication failed. Please try again.";
        }
    }
}
