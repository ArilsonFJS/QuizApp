package com.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.viewmodel.AuthViewModel

class EntrarFragment : Fragment() {

    lateinit var viewModel : AuthViewModel
    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_entrar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        val editEmail = view.findViewById<EditText>(R.id.editEmailEntrar)
        val editSenha = view.findViewById<EditText>(R.id.editSenhaEntrar)
        val inscrevaseText = view.findViewById<TextView>(R.id.textInscrevase)
        val btnEntrar = view.findViewById<Button>(R.id.btnEntrar)

        inscrevaseText.setOnClickListener {
            navController.navigate(R.id.action_entrarFragment_to_inscrevaseFragment)
        }

        btnEntrar.setOnClickListener {
            val email = editEmail.text.toString();
            val senha = editSenha.text.toString()

            if(!email.isEmpty() && !senha.isEmpty()){
                viewModel.entrar(email, senha)
                Toast.makeText(context, "Login com sucesso", Toast.LENGTH_SHORT).show()
                viewModel.firebaseUserMutableLiveData.observe(viewLifecycleOwner, Observer {
                    if (it != null){
                        navController.navigate(R.id.action_entrarFragment_to_listaFragment)
                    }
                })
            }else{
                Toast.makeText(context,"Por favor, insira e-mail e senha", Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

    }
}