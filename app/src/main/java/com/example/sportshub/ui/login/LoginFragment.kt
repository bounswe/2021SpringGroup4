package com.example.sportshub.ui.login

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sportshub.R
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
            var result = loginViewModel.tryLogin(binding.editTextEmail.text.toString(),binding.editTextPassword.text.toString())
            if(result){
                //Start Main Activity and Stop Login Activity
               //val intent = Intent(requireContext(),MainActivity::class.java)
                //startActivity(intent)
                Toast.makeText(requireContext(),binding.editTextPassword.text,Toast.LENGTH_LONG).show()

            }else{

            }
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        binding.editTextEmail.requestFocus()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}