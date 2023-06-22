package com.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.google.android.play.core.integrity.e
import com.viewmodel.PerguntaViewModel

private lateinit var navController: NavController
private lateinit var viewModel: PerguntaViewModel
private lateinit var respostaCertas: TextView
private lateinit var respostaErradas: TextView
private lateinit var naoResposdidas: TextView
private lateinit var porcetagem: TextView
private lateinit var pontuacaoProgressBar: ProgressBar
private var quizId: String = ""
private lateinit var btnHome: Button;

class ResultadoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(PerguntaViewModel::class.java);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resultado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        respostaCertas = view.findViewById(R.id.textRespostasCertas)
        respostaErradas = view.findViewById(R.id.textRespostasErradas)
        naoResposdidas = view.findViewById(R.id.textNaoRepondido)
        porcetagem = view.findViewById(R.id.textResultadoPorcentagemBar)
        pontuacaoProgressBar = view.findViewById(R.id.resultCountProgressBar)
        btnHome = view.findViewById(R.id.btnHome)


        btnHome.setOnClickListener {
            navController.navigate(R.id.action_resultadoFragment_to_listaFragment)
        }

        quizId = arguments?.let { ResultadoFragmentArgs.fromBundle(it).quizId }.toString()
        viewModel.setQuizId(quizId)
        viewModel.getResults()
        viewModel.resultMutableLiveData.observe(viewLifecycleOwner, Observer { result ->
            if(result != null){
                var correta : Long = result["correta"]!!
                var errada : Long = result["errada"]!!
                var semResposta : Long = result["semResposta"]!!

                respostaCertas.text = correta.toString()
                respostaErradas.text = errada.toString()
                naoResposdidas.text = semResposta.toString()

                var total : Long = correta + errada + semResposta
                var porcet : Long = (correta * 100)/total

                porcetagem.text = porcet.toString()
                pontuacaoProgressBar.progress = porcet.toInt()
            }else{
                Log.d("QUIZ ERROR", "onError")
            }
        })

    }
}