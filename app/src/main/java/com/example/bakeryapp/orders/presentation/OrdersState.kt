package com.example.bakeryapp.orders.presentation

import com.example.bakeryapp.catalog.presentation.Product

data class OrdersState(
    val orders: List<Order>,
)

data class Order(
    val id: Int,
    val title: String,
    val products: String,
    val price: Float,
)