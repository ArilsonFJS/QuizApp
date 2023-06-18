package com.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Model.QuizListaModel;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class QuizListaAdapter extends RecyclerView.Adapter<QuizListaAdapter.QuizListaViewHolder>{

    private List<QuizListaModel> quizListaModel;

    public void setQuizListaModel(List<QuizListaModel> quizListaModel) {
        this.quizListaModel = quizListaModel;
    }

    @NonNull
    @Override
    public QuizListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cada_quiz, parent, false);
        return new QuizListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizListaViewHolder holder, int position) {
            QuizListaModel model = quizListaModel.get(position);
            holder.titulo.setText(model.getTitulo());
            Glide.with(holder.itemView).load(model.getImagem()).into(holder.quizImagem);
    }

    @Override
    public int getItemCount() {
        if(quizListaModel == null){
            return 0;
        }else {
            return quizListaModel.size();
        }

    }

    public class QuizListaViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo;
        private ImageView quizImagem;

        public QuizListaViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.quizTituloLista);
            quizImagem = itemView.findViewById(R.id.quizImagemLista);
        }
    }

}
