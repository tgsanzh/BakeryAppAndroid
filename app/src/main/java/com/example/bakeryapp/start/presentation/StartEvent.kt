package com.example.bakeryapp.start.presentation

sealed interface StartEvent {
    object openScene : StartEvent
}