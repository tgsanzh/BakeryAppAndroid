package com.example.bakeryapp.register.presentation

data class RegisterState(
    val number: String,
    val password: String,
    val confirmPassword: String,
    val errorText: String,
    val showError: Boolean,
    val buttonEnabled: Boolean,
)