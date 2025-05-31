package com.example.bakeryapp.orders.data

import com.example.bakeryapp.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class OrdersRepositoryImpl(val client: HttpClient) : OrdersRepository {
    override suspend fun loadOrders(): List<OrderDTO> {
        val request: List<OrderDTO> = client.get("${BASE_URL}/orders/").body()
        return request
    }
}