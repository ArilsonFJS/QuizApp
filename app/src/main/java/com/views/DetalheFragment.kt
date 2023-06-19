package com.views

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.Model.QuizListaModel
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.viewmodel.QuizListaViewModel
import com.views.DetalheFragmentDirections.ActionDetalheFragmentToQuizFragment

class DetalheFragment : Fragment() {

    private lateinit var titulo : TextView
    private lateinit var dificuldade: TextView
    private lateinit var totalPerguntas: TextView

    private lateinit var btnInicarQuiz: Button
    private lateinit var navController: NavController
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: QuizListaViewModel

    private lateinit var topicImagem: ImageView


    private var position : Int = 0
    private var quizId: String = ""
    private var totalQuizCount : Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalhe, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuizListaViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titulo = view.findViewById(R.id.textDetalheFragmento)
        dificuldade = view.findViewById(R.id.textFragDificuldade)
        totalPerguntas = view.findViewById(R.id.textFragQtdPerguntas)
        btnInicarQuiz = view.findViewById(R.id.bntComecar)
        progressBar = view.findViewById(R.id.detalheProgressBar)

        topicImagem = view.findViewById(R.id.detalheFragmentoImage)
        navController = Navigation.findNavController(view)

        if (arguments != null) {
            position = DetalheFragmentArgs.fromBundle(arguments!!).position
        } else {
            position = 0
            Toast.makeText(requireContext(), "Os argumentos não estão disponíveis.", Toast.LENGTH_SHORT).show()
        }

        viewModel.quizListaLiveData.observe(viewLifecycleOwner, Observer <List<QuizListaModel>>{
            val quizListaModel: List<QuizListaModel> = it ?: emptyList()
            val quiz = quizListaModel.get(position)

            dificuldade.text = quiz.dificuldade
            titulo.text = quiz.titulo
            totalPerguntas.text = quiz.perguntas.toString()
            Glide.with(view).load(quiz.imagem).into(topicImagem)

            val handler = Handler()
            handler.postDelayed(Runnable () {
                progressBar.visibility = View.GONE
            },200);

            totalQuizCount = quiz.perguntas
            quizId = quiz.quizID
        })

        btnInicarQuiz.setOnClickListener {

            val action: ActionDetalheFragmentToQuizFragment = DetalheFragmentDirections
                .actionDetalheFragmentToQuizFragment()

            action.quizId = quizId
            action.totalQuizCount = totalQuizCount
            navController.navigate(action)
        }
    }

}