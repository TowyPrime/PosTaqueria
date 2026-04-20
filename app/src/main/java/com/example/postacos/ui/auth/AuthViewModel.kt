package com.example.postacos.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postacos.data.AuthRepository

class AuthViewModel(private val repository: AuthRepository): ViewModel(){


    private val _loginState = MutableLiveData<ResultState>()
    val loginState: LiveData<ResultState> = _loginState

    private val _signUpState = MutableLiveData<ResultState>()
    val signUpState: LiveData<ResultState> = _signUpState

    fun signUp(name: String, email: String, password: String){
        _signUpState.value = ResultState.Loading

        repository.signUp(name, email, password) { success, error ->

            _signUpState.value = if (success) {
                ResultState.Success
            } else {
                ResultState.Error(error ?: "Error al registrar")
            }
        }
    }

    fun login(email: String, password: String){
        _loginState.value = ResultState.Loading

        repository.login(email,password){success,error ->
            _loginState.value = if (success){
                ResultState.Success
            }else{
                ResultState.Error(error?: "Error desconocido")
            }
        }
    }

}