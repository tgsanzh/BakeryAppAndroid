package com.example.bakeryapp.register.presentation

sealed interface RegisterEvent {
    data class register(
        val value: String
    ) : RegisterEvent

    data class onNumberChanged(
        val value: String
    ) : RegisterEvent

    data class onPasswordChanged(
        val value: String
    ) : RegisterEvent

    data class onConfirmPasswordChanged(
        val value: String
    ) : RegisterEvent

    object toLogin : RegisterEvent
}