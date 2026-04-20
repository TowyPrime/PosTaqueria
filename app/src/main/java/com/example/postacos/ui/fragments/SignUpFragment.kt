package com.example.postacos.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.postacos.R
import com.example.postacos.data.AuthRepository
import com.example.postacos.databinding.FragmentSignUpBinding
import com.example.postacos.ui.MyApp
import com.example.postacos.ui.auth.AuthViewModel
import com.example.postacos.ui.auth.AuthViewModelFactory
import com.example.postacos.ui.auth.ResultState


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObservers()
        listenerBtnSignUp()
    }

    private fun setupViewModel() {
        val db = (requireActivity().application as MyApp).database
        val repository = AuthRepository(db.userDao())
        val factory = AuthViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.signUpState.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {

                }

                is ResultState.Success -> {
                    Toast.makeText(requireContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                }

                is ResultState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun listenerBtnSignUp() {
        binding.btnSignUp.setOnClickListener {

            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val repeatPassword = binding.etRepeatPassword.text.toString().trim()

            if (verifyFields(name, email, password, repeatPassword)) {
                if (matchPassword(password, repeatPassword)) {
                    createUser(name, email, password)
                }
            }
        }
    }

    private fun matchPassword(password: String, repeatPassword: String): Boolean {
        return if (password == repeatPassword) {
            binding.tilPassword.error = null
            binding.tilRepeatPassword.error = null
            true
        } else {
            binding.tilPassword.error = getString(R.string.error_match_password)
            binding.tilRepeatPassword.error = getString(R.string.error_match_password)
            false
        }
    }

    private fun verifyFields(
        name: String,
        email: String,
        password: String,
        repeatPassword: String
    ): Boolean {

        var isValid = true

        if (name.isBlank()) {
            binding.tilName.error = getString(R.string.error_blank)
            isValid = false
        } else {
            binding.tilName.error = null
        }

        if (email.isBlank()) {
            binding.tilEmail.error = getString(R.string.error_blank)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.error_email)
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (password.isBlank()) {
            binding.tilPassword.error = getString(R.string.error_blank_password)
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        if (repeatPassword.isBlank()) {
            binding.tilRepeatPassword.error = getString(R.string.error_blank_password)
            isValid = false
        } else {
            binding.tilRepeatPassword.error = null
        }

        return isValid
    }

    private fun createUser(name: String, email: String, password: String) {
        viewModel.signUp(name, email, password)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
