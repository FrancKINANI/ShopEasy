package com.ma.shopeasy.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.ma.shopeasy.data.repository.AuthRepository;
import com.ma.shopeasy.utils.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends ViewModel {

    private final AuthRepository repository;
    private final MutableLiveData<FirebaseUser> _user = new MutableLiveData<>();
    public final LiveData<FirebaseUser> user = _user;

    @Inject
    public AuthViewModel(AuthRepository repository) {
        this.repository = repository;
        _user.setValue(repository.getCurrentUser());
    }

    public LiveData<Resource<FirebaseUser>> login(String email, String password) {
        return repository.login(email, password);
    }

    public LiveData<Resource<FirebaseUser>> signup(String email, String password, String name) {
        return repository.register(email, password, name);
    }

    public void logout() {
        repository.logout();
        _user.setValue(null);
    }
}
