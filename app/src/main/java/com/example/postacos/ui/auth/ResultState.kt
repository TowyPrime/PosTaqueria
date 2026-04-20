package com.example.postacos.ui.auth

sealed class ResultState {
    object Loading: ResultState()
    object Success: ResultState()
    data class Error(val message: String): ResultState()
}