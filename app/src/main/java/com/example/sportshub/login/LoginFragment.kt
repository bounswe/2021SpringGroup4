package com.example.sportshub.login

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sportshub.MainActivity
import com.example.sportshub.R
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.login.model.LoginResponseModel
import com.example.sportshub.databinding.FragmentLoginBinding

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
            loginViewModel.tryLogin(requireContext(), binding.editTextUsername.text.toString(),binding.editTextPassword.text.toString(),
                object: LoginListener() {
                    override fun onError(loginResponseModel: LoginResponseModel?) {
                        binding.editTextUsername.text.clear()
                        binding.editTextPassword.text.clear()
                    }

                    override fun onResponse(loginResponseModel: LoginResponseModel?) {
                        SingletonRequestQueueProvider.setAccessToken(loginResponseModel!!.access)
                        SingletonRequestQueueProvider.saveCredentials(binding.editTextUsername.text.toString(),binding.editTextPassword.text.toString())
                        //Start Main Activity and Stop Login Activity
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    }
                })
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        binding.editTextUsername.requestFocus()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}