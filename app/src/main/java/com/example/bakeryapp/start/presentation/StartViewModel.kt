package com.example.bakeryapp.start.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bakeryapp.catalog.data.CatalogRepository
import com.example.bakeryapp.login.data.LoginRepository
import com.example.bakeryapp.login.data.LoginRequest
import com.example.bakeryapp.resetHttpClient
import com.example.bakeryapp.start.data.StartRepository
import com.example.bakeryapp.start.data.StartRequest
import com.example.bakeryapp.utils.SharedPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartViewModel(val sharedPrefs: SharedPrefs, val repository: StartRepository) : ViewModel() {
    fun dispatch(
        event: StartEvent,
        navigate: (String) -> Unit
    ) {
        when (event) {
            StartEvent.openScene -> {
                viewModelScope.launch {
                    repository.checkToken(
                        data = StartRequest(sharedPrefs.getToken())
                    ).onSuccess { response ->
                        navigate("catalog")
                    }.onFailure { error ->
                        navigate("login")
                        Log.d("DATA", error.message.toString())
                    }
                }
            }
        }
    }
}