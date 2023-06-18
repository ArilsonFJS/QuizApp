package com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.google.firebase.auth.FirebaseUser;
import com.repository.AuthRepository;

public class AuthViewModel extends AndroidViewModel {

    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseUser currentUser;
    private AuthRepository repository;

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);

        repository = new AuthRepository(application);
        currentUser = repository.getCurrentUser();
        firebaseUserMutableLiveData = repository.getFirebaseUserMutableLiveData();
    }

    public void inscrever(String email, String senha){
        repository.inscrever(email, senha);
    }

    public void entrar(String email, String senha){
        repository.entrar(email, senha);
    }

    public void sair(){
        repository.sair();
    }

}
