package com.Model;

import com.google.firebase.firestore.DocumentId;

public class PerguntaModel {

    @DocumentId
    private String perguntaID;

    private String pergunta, resposta, opcao_a, opcao_b, opcao_c, opcao_d, opcao_e;
    private long tempo;

    public PerguntaModel(){}

    public PerguntaModel(String perguntaID, String pergunta, String resposta, String opcao_a, String opcao_b, String opcao_c, String opcao_d, String opcao_e, long tempo) {
        this.perguntaID = perguntaID;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.opcao_a = opcao_a;
        this.opcao_b = opcao_b;
        this.opcao_c = opcao_c;
        this.opcao_d = opcao_d;
        this.opcao_e = opcao_e;
        this.tempo = tempo;
    }

    public String getPerguntaID() {
        return perguntaID;
    }

    public void setPerguntaID(String perguntaID) {
        this.perguntaID = perguntaID;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getOpcao_a() {
        return opcao_a;
    }

    public void setOpcao_a(String opcao_a) {
        this.opcao_a = opcao_a;
    }

    public String getOpcao_b() {
        return opcao_b;
    }

    public void setOpcao_b(String opcao_b) {
        this.opcao_b = opcao_b;
    }

    public String getOpcao_c() {
        return opcao_c;
    }

    public void setOpcao_c(String opcao_c) {
        this.opcao_c = opcao_c;
    }

    public String getOpcao_d() {
        return opcao_d;
    }

    public void setOpcao_d(String opcao_d) {
        this.opcao_d = opcao_d;
    }

    public String getOpcao_e() {
        return opcao_e;
    }

    public void setOpcao_e(String opcao_e) {
        this.opcao_e = opcao_e;
    }

    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }
}
