package com.example.bakeryapp.orders.domain

data class Orders(
    val orderId: Int,
    val status: String,
    val totalAmount: Double,
    val deliveryAddress: String,
    val createdAt: String,
    val items: List<OrdersItems>
)

data class OrdersItems(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val pricePerItem: Double
)