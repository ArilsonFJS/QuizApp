package com.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.viewmodel.PerguntaViewModel
import java.util.Objects


class QuizFragment : Fragment(), View.OnClickListener {

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
    private lateinit var tempoCount : TextView;
    private lateinit var btnFecharQuiz : ImageView
    private lateinit var quizId: String
    private var totalPerguntas : Long = 0
    private var perguntaAtualNum : Int = 0
    private var podePerguntar : Boolean = false
    private var tempo: Long = 0
    private lateinit var countDownTimer: CountDownTimer
    private var naoRespondida : Int = 0
    private var respostaCerta : Int = 0
    private var respostaErrada : Int = 0
    private var resposta : String = ""


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
        tempoCount = view.findViewById(R.id.textTempo)
        perguntaNumero = view.findViewById(R.id.quizCountPerguntas)
        progressBar = view.findViewById(R.id.quizCountProgressBar)

        quizId = arguments?.let { QuizFragmentArgs.fromBundle(it).quizId }.toString()
        totalPerguntas = arguments?.let { QuizFragmentArgs.fromBundle(it).totalQuizCount }!!
        viewModel.setQuizId(quizId)

        btnOpcA.setOnClickListener (this)
        btnOpcB.setOnClickListener (this)
        btnOpcC.setOnClickListener (this)
        btnOpcD.setOnClickListener (this)
        btnOpcE.setOnClickListener (this)
        btnProximaPergunta.setOnClickListener (this)

        btnFecharQuiz.setOnClickListener( View.OnClickListener {
            navController.navigate(R.id.action_quizFragment_to_listaFragment)
        })

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

        perguntaAtualNum = i
        viewModel.perguntaMutableLiveData.observe(viewLifecycleOwner, Observer {
            pergunta.text = it[i-1].pergunta
            btnOpcA.text = it [i-1].opcao_a
            btnOpcB.text = it [i-1].opcao_b
            btnOpcC.text = it [i-1].opcao_c
            btnOpcD.text = it [i-1].opcao_d
            btnOpcE.text = it [i-1].opcao_e
            tempo = it [i-1].tempo
            resposta = it [i-1].resposta
        })

        startTempo()
        podePerguntar = true
    }

    private fun startTempo(){
        tempoCount.text = tempo.toString()
        progressBar.visibility = View.VISIBLE
        countDownTimer = object : CountDownTimer(tempo * 1000,1000 ) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                // Ação a ser executada a cada intervalo de tempo
                tempoCount.text = ((millisUntilFinished / 1000).toString())

                var porcetagem : Long = millisUntilFinished/(tempo*10)
                progressBar.progress = porcetagem.toInt()
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                // Ação a ser executada quando a contagem regressiva terminar
                podePerguntar = false
                feedbackResposta.text = "O tempo acabou sem um resposta selecionada"
                naoRespondida ++
                showNextBtn()
            }
        }.start()

    }

    @SuppressLint("SetTextI18n")
    private fun showNextBtn(){
        val totalPergunta = totalPerguntas.toInt()
        if (totalPergunta == perguntaAtualNum){
            btnProximaPergunta.text = "Próxima!"
            btnProximaPergunta.isEnabled = true
            btnProximaPergunta.visibility= View.VISIBLE
        }else{
            btnProximaPergunta.visibility= View.VISIBLE
            btnProximaPergunta.isEnabled = true
            feedbackResposta.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.btnOpcao1 ->{
                verficarResposta(btnOpcA)
            }
            R.id.btnOpcao2 ->{
                verficarResposta(btnOpcB)
            }
            R.id.btnOpcao3 ->{
                verficarResposta(btnOpcC)
            }
            R.id.btnOpcao4 ->{
                verficarResposta(btnOpcD)
            }
            R.id.btnOpcao5 ->{
                verficarResposta(btnOpcE)
            }
            R.id.btnProxPergunta ->{
                val totalPergunta = totalPerguntas.toInt()
                if(perguntaAtualNum == totalPergunta){
                    enviarResultado()
                }else{
                    perguntaAtualNum ++
                    carregarPerguntas(perguntaAtualNum)
                    resetarOpcoes()
                }
            }
        }

    }

    private fun resetarOpcoes() {
        feedbackResposta.visibility = View.INVISIBLE
        btnProximaPergunta.visibility = View.INVISIBLE
        btnProximaPergunta.isEnabled = false
        btnOpcA.background = context?.let { ContextCompat.getDrawable(it, R.color.azul_escuro) }
        btnOpcB.background = context?.let { ContextCompat.getDrawable(it, R.color.azul_escuro) }
        btnOpcC.background = context?.let { ContextCompat.getDrawable(it, R.color.azul_escuro) }
        btnOpcD.background = context?.let { ContextCompat.getDrawable(it, R.color.azul_escuro) }
        btnOpcE.background = context?.let { ContextCompat.getDrawable(it, R.color.azul_escuro) }
    }


    private fun enviarResultado() {
        var resultMap = HashMap<String, Any>();
        resultMap.put("correta", respostaCerta);
        resultMap.put("errada", respostaErrada);
        resultMap.put("semResposta", naoRespondida);

       viewModel.addResults(resultMap)
        navController.navigate(R.id.action_quizFragment_to_resultadoFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun verficarResposta(button: Button){
        if(podePerguntar){
            if(resposta.equals(button.text)){
                button.background = context?.let { ContextCompat.getDrawable(it, R.color.teal_700) }
                respostaCerta ++
                feedbackResposta.text = "Certa!"
            }else{
                button.background = context?.let { ContextCompat.getDrawable(it, R.color.vermelho) }
                respostaErrada++
                feedbackResposta.text = "Errada! \nResposta certa: $resposta"
            }
        }
        podePerguntar= false
        countDownTimer.cancel()
        showNextBtn()
    }

}
