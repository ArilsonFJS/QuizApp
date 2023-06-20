package com.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Model.PerguntaModel;
import com.repository.PerguntaRepository;

import java.util.List;

public class PerguntaViewModel extends ViewModel implements PerguntaRepository.OnPerguntaLoad {

    private MutableLiveData<List<PerguntaModel>> perguntaMutableLiveData;
    private PerguntaRepository repository;

    public MutableLiveData<List<PerguntaModel>> getPerguntaMutableLiveData() {
        return perguntaMutableLiveData;
    }

    public void setPerguntaMutableLiveData(MutableLiveData<List<PerguntaModel>> perguntaMutableLiveData) {
        this.perguntaMutableLiveData = perguntaMutableLiveData;
    }

    public PerguntaViewModel(){
        perguntaMutableLiveData = new MutableLiveData<>();
        repository = new PerguntaRepository(this);
    }

    public void setQuizId(String quizId){
        repository.setQuizId(quizId);
        repository.getPergunta();
    }
    @Override
    public void onLoad(List<PerguntaModel> perguntaModels) {
        perguntaMutableLiveData.setValue(perguntaModels);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QUIZ APP ERROR", "onError" + e.getMessage());
    }
}
