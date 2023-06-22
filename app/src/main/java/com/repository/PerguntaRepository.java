package com.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.Model.PerguntaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PerguntaRepository {

    private FirebaseFirestore firebaseFirestore;
    private String quizId;
    private HashMap<String, Long> resultMap = new HashMap<>();
    private OnPerguntaLoad onPerguntaLoad;
    private OnResultAdded onResultAdded;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private OnResultLoad onResultLoad;


    public void getResultado(){
        firebaseFirestore.collection("QuizApp").document(quizId).collection("resultado")
                .document(currentUserId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            resultMap.put("correta", task.getResult().getLong("correta"));
                            resultMap.put("errada", task.getResult().getLong("errada"));
                            resultMap.put("semResposta", task.getResult().getLong("semResposta"));
                            onResultLoad.onResultLoad(resultMap);
                        }else{
                            onResultLoad.onError(task.getException());
                        }
                    }
                });
    }

    public void addResultado(HashMap<String, Object> resultMap){
        firebaseFirestore.collection("QuizApp").document(quizId).collection("resultado")
                .document(currentUserId)
                .set(resultMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Lógica para tratar a conclusão da operação
                        if(task.isSuccessful()){
                            onResultAdded.onSubmit();
                        }else {
                            onResultAdded.onError(task.getException());
                        }
                    }
                });

    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public PerguntaRepository (OnPerguntaLoad onPerguntaLoad, OnResultAdded onResultAdded, OnResultLoad onResultLoad){
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onPerguntaLoad = onPerguntaLoad;
        this.onResultAdded = onResultAdded;
        this.onResultLoad = onResultLoad;

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

    public interface OnResultLoad{
        void onResultLoad(HashMap<String, Long> resultMap);
        void onError(Exception e);
    }

    public interface OnPerguntaLoad {
        void onLoad(List<PerguntaModel> perguntaModels);
        void onError(Exception e);
    }

    public interface OnResultAdded{
        boolean onSubmit();
        void onError(Exception e);
    }
}
