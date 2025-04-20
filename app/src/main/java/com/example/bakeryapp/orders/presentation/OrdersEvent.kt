package com.example.bakeryapp.orders.presentation

sealed interface OrdersEvent {
    data class onCancel (
        val id: Int
    ) : OrdersEvent
}