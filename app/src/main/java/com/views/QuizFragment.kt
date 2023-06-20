package com.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.Model.PerguntaModel
import com.example.myapplication.R
import com.viewmodel.PerguntaViewModel


class QuizFragment : Fragment() {

    private lateinit var viewModel: PerguntaViewModel
    private lateinit var navController: NavController

    private lateinit var progressBar: ProgressBar
    private lateinit var btnOpcA : Button
    private lateinit var btnOpcB : Button
    private lateinit var btnOpcC : Button
    private lateinit var btnOpcD : Button
    private lateinit var btnOpcE : Button
    private lateinit var btnProximaPergunta : Button

    private lateinit var pergunta: TextView;
    private lateinit var feedbackResposta: TextView;
    private lateinit var perguntaNumero: TextView;
    private lateinit var tempo : TextView;

    private lateinit var btnFecharQuiz : ImageView
    private lateinit var quizId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(PerguntaViewModel::class.java);
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        btnFecharQuiz = view.findViewById(R.id.btn_sair_quiz)
        btnOpcA = view.findViewById(R.id.btnOpcao1)
        btnOpcB = view.findViewById(R.id.btnOpcao2)
        btnOpcC = view.findViewById(R.id.btnOpcao3)
        btnOpcD = view.findViewById(R.id.btnOpcao4)
        btnOpcE = view.findViewById(R.id.btnOpcao5)
        btnProximaPergunta = view.findViewById(R.id.btnProxPergunta)
        feedbackResposta = view.findViewById(R.id.textFeedbackResposta)
        pergunta = view.findViewById(R.id.textPerguntas)
        tempo = view.findViewById(R.id.textTempo)
        perguntaNumero = view.findViewById(R.id.quizCountPerguntas)
        progressBar = view.findViewById(R.id.quizCountProgressBar)

        quizId = arguments?.let { QuizFragmentArgs.fromBundle(it).quizId }.toString()
        viewModel.setQuizId(quizId)

        carregarDados()
    }

    private fun carregarDados(){
        ativarOpcoes()
        carregarPerguntas(1)
    }

    private fun ativarOpcoes(){
        btnOpcA.visibility = View.VISIBLE
        btnOpcB.visibility = View.VISIBLE
        btnOpcC.visibility = View.VISIBLE
        btnOpcD.visibility = View.VISIBLE
        btnOpcE.visibility = View.VISIBLE

        btnOpcA.isEnabled = true
        btnOpcB.isEnabled = true
        btnOpcC.isEnabled = true
        btnOpcD.isEnabled = true
        btnOpcE.isEnabled = true

        feedbackResposta.visibility = View.INVISIBLE
        btnProximaPergunta.visibility = View.INVISIBLE


    }
    private fun carregarPerguntas(i: Int) {
        viewModel.getPerguntaMutableLiveData().observe(getViewLifecycleOwner(), Observer {
            pergunta.text = it[i - 1].pergunta
            btnOpcA.text = it [i-1].opcao_a
            btnOpcB.text = it [i-1].opcao_b
            btnOpcC.text = it [i-1].opcao_c
            btnOpcD.text = it [i-1].opcao_d
            btnOpcE.text = it [i-1].opcao_e
        })
    }

}