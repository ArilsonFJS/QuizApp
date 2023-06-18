package com.views

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.viewmodel.AuthViewModel
import org.checkerframework.checker.nullness.qual.NonNull


class SplashFragment : Fragment() {

    lateinit var viewModel : AuthViewModel;
    lateinit var navController : NavController;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        navController= Navigation.findNavController(view)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val handler = Handler()

        handler.postDelayed({
            if(viewModel.currentUser != null){
                navController.navigate(R.id.action_splashFragment2_to_listaFragment);
            }else{
                navController.navigate(R.id.action_splashFragment2_to_entrarFragment)
            }
        }, 4000)

    }
}