package com.example.sportshub.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sportshub.R
import com.example.sportshub.databinding.FragmentLoginBinding
import com.example.sportshub.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerViewModel =
            ViewModelProvider(this).get(RegisterViewModel::class.java)

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnRegister.setOnClickListener {
            var success = registerViewModel.tryregister(binding.editTextNewUsername.text.toString(),binding.editTextNewEmail.text.toString(),binding.editTextNewPassword.text.toString(),binding.editTextConfirmPassword.text.toString())
            if(success){
                Toast.makeText(requireContext(),"Registration Successful",Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }else{
                Toast.makeText(requireContext(),"Registration Failed",Toast.LENGTH_LONG).show()
                binding.editTextNewEmail.text.clear()
                binding.editTextNewPassword.text.clear()
                binding.editTextConfirmPassword.text.clear()
            }
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        binding.editTextNewEmail.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}