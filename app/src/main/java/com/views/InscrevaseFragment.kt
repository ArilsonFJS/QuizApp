package com.views

import android.os.Bundle
import android.os.Handler
import android.util.Log
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

class InscrevaseFragment : Fragment() {

    lateinit var viewModel : AuthViewModel;
    lateinit var navController : NavController;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inscrevase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        val editEmail = view.findViewById<EditText>(R.id.editEmailInscrevase)
        val editSenha = view.findViewById<EditText>(R.id.editSenhaInscrevase)
        val entrarText = view.findViewById<TextView>(R.id.textEntrar)
        val btnInsecreva = view.findViewById<Button>(R.id.btnInscrever)

        entrarText.setOnClickListener {
            navController.navigate(R.id.action_inscrevaseFragment_to_entrarFragment)
        }

        btnInsecreva.setOnClickListener {
            val email = editEmail.text.toString();
            val senha = editSenha.text.toString()

            if(!email.isEmpty() && !senha.isEmpty()){
                    viewModel.inscrever(email, senha)
                    Toast.makeText(context, "Registrado com sucesso", Toast.LENGTH_SHORT).show()
                    viewModel.firebaseUserMutableLiveData.observe(viewLifecycleOwner, Observer {
                        if (it != null){
                            navController.navigate(R.id.action_inscrevaseFragment_to_entrarFragment)
                        }
                    })
            }else{
                Toast.makeText(context,"Por favor, insira e-mail e senha",Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

    }
}