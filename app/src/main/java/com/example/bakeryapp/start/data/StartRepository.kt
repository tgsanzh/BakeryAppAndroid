package com.example.bakeryapp.start.data

import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StartRepository: KoinComponent {
    val client: HttpClient by inject()
}