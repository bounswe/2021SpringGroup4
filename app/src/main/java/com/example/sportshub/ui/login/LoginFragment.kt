package com.example.sportshub.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.sportshub.MainActivity
import com.example.sportshub.R
import com.example.sportshub.databinding.FragmentDashboardBinding
import com.example.sportshub.databinding.FragmentLoginBinding
import com.example.sportshub.ui.dashboard.DashboardViewModel

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnNavigateToRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            var result = loginViewModel.tryLogin(binding.editTextEmail.toString(),binding.editTextPassword.toString())
            if(result){
                //Start Main Activity and Stop Login Activity
               val intent = Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
                
            }else{

            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}