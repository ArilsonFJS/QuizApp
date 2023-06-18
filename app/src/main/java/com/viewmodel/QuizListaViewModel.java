package com.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Model.QuizListaModel;
import com.repository.QuizListaRepository;

import java.util.List;

public class QuizListaViewModel extends ViewModel implements QuizListaRepository.onFirestoreTaskComplete {

    private MutableLiveData<List<QuizListaModel>> quizListaLiveData = new MutableLiveData<>();
    private QuizListaRepository repository = new QuizListaRepository(this);

    public MutableLiveData<List<QuizListaModel>> getQuizListaLiveData() {
        return quizListaLiveData;
    }

    public QuizListaViewModel(){
        repository.getQuizData();
    }
    @Override
    public void quizDataLoaded(List<QuizListaModel> quizListaModels) {
        quizListaLiveData.setValue(quizListaModels);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QUIZ ERROR", "onError" + e.getMessage());
    }
}
