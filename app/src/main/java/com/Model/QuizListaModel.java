package com.Model;

import com.google.firebase.firestore.DocumentId;

public class QuizListaModel {

    @DocumentId
    private String quizID;
    private String titulo, imagem, dificuldade;
    private long perguntas;

    public QuizListaModel(){}
    public QuizListaModel(String quizID, String titulo, String imagem, String dificuldade, long perguntas) {
        this.quizID = quizID;
        this.titulo = titulo;
        this.imagem = imagem;
        this.dificuldade = dificuldade;
        this.perguntas = perguntas;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public long getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(long perguntas) {
        this.perguntas = perguntas;
    }
}
