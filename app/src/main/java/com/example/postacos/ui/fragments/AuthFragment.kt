package com.example.postacos.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.postacos.R
import com.example.postacos.databinding.FragmentAuthBinding
import kotlinx.coroutines.withContext


class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentAuthBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerBtnEnter()

    }

    private fun listenerBtnEnter(){
        binding.btnEnter.setOnClickListener {
            launchLoginFragment()
        }
    }

    private fun launchLoginFragment(){
        findNavController().navigate(R.id.action_authFragment_to_loginFragment)
    }

}