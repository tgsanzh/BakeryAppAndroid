package com.example.bakeryapp.login.presentation

sealed interface LoginEvent {
    data class login(
        val value: String
    ) : LoginEvent

    data class onNumberChanged(
        val value: String
    ) : LoginEvent

    data class onPasswordChanged(
        val value: String
    ) : LoginEvent

    object toRegister : LoginEvent
}