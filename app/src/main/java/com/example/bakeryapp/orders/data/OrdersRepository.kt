package com.example.bakeryapp.orders.data

interface OrdersRepository {
    suspend fun loadOrders(): Result<List<OrderDTO>>
}