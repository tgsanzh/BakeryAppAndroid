package com.example.bakeryapp.orders.data

interface OrdersRepository {
    suspend fun loadOrders(): List<OrderDTO>
}