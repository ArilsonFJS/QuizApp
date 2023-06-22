package com.Model;

import com.google.firebase.firestore.DocumentId;

public class QuizListaModel {

    @DocumentId
    private String quizId;
    private String titulo, imagem, dificuldade;
    private long perguntas;

    public QuizListaModel(){}
    public QuizListaModel(String quizId, String titulo, String imagem, String dificuldade, long perguntas) {
        this.quizId = quizId;
        this.titulo = titulo;
        this.imagem = imagem;
        this.dificuldade = dificuldade;
        this.perguntas = perguntas;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
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
