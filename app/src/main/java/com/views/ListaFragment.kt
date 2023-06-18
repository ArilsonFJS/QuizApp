package com.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Adapter.QuizListaAdapter
import com.Model.QuizListaModel
import com.example.myapplication.R
import com.viewmodel.AuthViewModel
import com.viewmodel.QuizListaViewModel


class ListaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var navController: NavController
    private lateinit var viewModel: QuizListaViewModel
    private lateinit var adapter: QuizListaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuizListaViewModel::class.java)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         recyclerView = view.findViewById(R.id.listaQuizRecyclerview)
         progressBar = view.findViewById(R.id.quizListaProgressBar)
         navController = Navigation.findNavController(view)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = QuizListaAdapter()
        recyclerView.adapter = adapter

        viewModel.quizListaLiveData.observe(viewLifecycleOwner, Observer<List<QuizListaModel>> { quizList ->

            val quizListaModel: List<QuizListaModel> = quizList ?: emptyList()

            progressBar.visibility = View.GONE
            adapter.setQuizListaModel(quizListaModel)
            adapter.notifyDataSetChanged()

        })

    }
}
