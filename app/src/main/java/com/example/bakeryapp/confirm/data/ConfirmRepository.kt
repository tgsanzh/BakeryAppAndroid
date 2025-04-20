package com.example.bakeryapp.confirm.data

import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConfirmRepository: KoinComponent {
    val client: HttpClient by inject()
}