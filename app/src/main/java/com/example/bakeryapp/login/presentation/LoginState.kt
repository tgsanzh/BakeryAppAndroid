package com.example.bakeryapp.login.presentation

data class LoginState(
    val number: String,
    val password: String,
    val errorText: String,
    val showError: Boolean,
    val buttonEnabled: Boolean
)