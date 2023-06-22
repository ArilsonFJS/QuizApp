package com.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Model.PerguntaModel;
import com.repository.PerguntaRepository;

import java.util.HashMap;
import java.util.List;

public class PerguntaViewModel extends ViewModel implements PerguntaRepository.OnPerguntaLoad, PerguntaRepository.OnResultAdded, PerguntaRepository.OnResultLoad {

    private MutableLiveData<List<PerguntaModel>> perguntaMutableLiveData;
    private PerguntaRepository repository;
    private MutableLiveData<HashMap<String, Long>> resultMutableLiveData;

    public MutableLiveData<HashMap<String, Long>> getResultMutableLiveData() {
        return resultMutableLiveData;
    }

    public void getResults(){
        repository.getResultado();
    }

    public MutableLiveData<List<PerguntaModel>> getPerguntaMutableLiveData() {
        return perguntaMutableLiveData;
    }

    public void setPerguntaMutableLiveData(MutableLiveData<List<PerguntaModel>> perguntaMutableLiveData) {
        this.perguntaMutableLiveData = perguntaMutableLiveData;
    }

    public PerguntaViewModel(){
        perguntaMutableLiveData = new MutableLiveData<>();
        resultMutableLiveData = new MutableLiveData<>();
        repository = new PerguntaRepository(this,this, this);
    }

    public void addResults(HashMap<String, Object> resultMap){
        repository.addResultado(resultMap);
    }

    public void setQuizId(String quizId){
        repository.setQuizId(quizId);
    }

    public void getPerguntas(){
        repository.getPergunta();
    }
    @Override
    public void onLoad(List<PerguntaModel> perguntaModels) {
        perguntaMutableLiveData.setValue(perguntaModels);
    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onResultLoad(HashMap<String, Long> resultMap) {
        resultMutableLiveData.setValue(resultMap);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QUIZ APP ERROR", "onError" + e.getMessage());
    }
}
