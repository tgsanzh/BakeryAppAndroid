package com.example.bakeryapp.orders.data

import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OrdersRepository: KoinComponent {
    val client: HttpClient by inject()
}