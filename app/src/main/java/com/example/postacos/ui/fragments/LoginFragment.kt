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
import com.example.postacos.databinding.FragmentLoginBinding
import com.example.postacos.ui.MyApp
import com.example.postacos.ui.auth.AuthViewModel
import com.example.postacos.ui.auth.AuthViewModelFactory
import com.example.postacos.ui.auth.ResultState


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObservers()
        listenerBtnLogin()
        listenerBtnSignUp()
    }

    private fun setupViewModel() {
        val db = (requireActivity().application as MyApp).database
        val repository = AuthRepository(db.userDao())
        val factory = AuthViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {

                is ResultState.Loading -> {
                    // opcional: loader
                }

                is ResultState.Success -> {
                    Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    launchDashboard()
                }

                is ResultState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun listenerBtnLogin() {
        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateFields(email, password)) {
                viewModel.login(email, password)
            }
        }
    }

    private fun listenerBtnSignUp() {
        binding.tvSignUp.setOnClickListener {
            launchSignUp()
        }
    }

    private fun validateFields(email: String, password: String): Boolean {

        var isValid = true

        // Email
        if (email.isBlank()) {
            binding.tilEmail.error = getString(R.string.error_blank)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.error_email)
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        // Password
        if (password.isBlank()) {
            binding.tilPassword.error = getString(R.string.error_blank_password)
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        return isValid
    }

    private fun launchDashboard() {
        findNavController().navigate(R.id.action_loginFragment_to_deshboardFragment)
    }

    private fun launchSignUp() {
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
