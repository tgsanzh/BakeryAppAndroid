package com.example.bakeryapp.start.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun StartScreen(onEvent: (StartEvent) -> Unit) {
    LaunchedEffect(Unit) {
        onEvent(StartEvent.openScene)
    }
}