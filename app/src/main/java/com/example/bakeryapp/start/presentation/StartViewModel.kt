package com.example.bakeryapp.start.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bakeryapp.utils.SharedPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartViewModel(val sharedPrefs: SharedPrefs) : ViewModel() {
    fun dispatch(
        event: StartEvent,
        navigate: (String) -> Unit
    ) {
        when (event) {
            StartEvent.openScene -> {
                viewModelScope.launch {
                    delay(2000)
                    if (sharedPrefs.getToken() != "") {
                        navigate("catalog")
                    } else {
                        navigate("login")
                    }
                }
            }
        }
    }
}