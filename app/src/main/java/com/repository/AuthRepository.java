package com.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public AuthRepository(Application application) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void inscrever(String email, String senha){
        firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                }else{
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void entrar(String email, String senha) {
        firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                }else{
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void  sair(){
        firebaseAuth.signOut();
    }
}
