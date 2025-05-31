package com.example.bakeryapp.orders.presentation

sealed interface OrdersEvent {
    object leaveAccount : OrdersEvent
    object loadData : OrdersEvent
}