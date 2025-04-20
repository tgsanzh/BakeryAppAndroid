package com.example.bakeryapp.confirm.presentation

sealed interface ConfirmEvent {
    data class confirmCode(
        val value: String
    ): ConfirmEvent

    data class onValueChanged(
        val value: String
    ): ConfirmEvent

    data object back: ConfirmEvent
}