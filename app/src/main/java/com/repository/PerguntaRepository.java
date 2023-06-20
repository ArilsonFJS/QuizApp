package com.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.Model.PerguntaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PerguntaRepository {

    private FirebaseFirestore firebaseFirestore;
    private String quizId;
    private OnPerguntaLoad onPerguntaLoad;

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public PerguntaRepository (OnPerguntaLoad onPerguntaLoad){
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onPerguntaLoad = onPerguntaLoad;

    }

    public void getPergunta() {
        firebaseFirestore.collection("QuizApp").document(quizId)
                .collection("perguntas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            onPerguntaLoad.onLoad(task.getResult().toObjects(PerguntaModel.class));
                        } else {
                            onPerguntaLoad.onError(task.getException());
                        }
                    }
                });
    }

    public interface OnPerguntaLoad {
        void onLoad(List<PerguntaModel> perguntaModels);
        void onError(Exception e);
    }
}
