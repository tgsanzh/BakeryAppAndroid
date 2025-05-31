package com.example.bakeryapp.orders.presentation

import com.example.bakeryapp.orders.domain.Orders

data class OrdersState(
    val orders: List<Orders>,
    val ordersLoaded: Boolean
)

