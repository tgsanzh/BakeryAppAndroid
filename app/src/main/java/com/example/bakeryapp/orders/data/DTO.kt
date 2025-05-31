package com.example.bakeryapp.orders.data

import kotlinx.serialization.Serializable

@Serializable
data class OrderDTO(
    val order_id: Int,
    val status: String,
    val total_amount: Double,
    val delivery_address: String,
    val created_at: String,
    val items: List<ItemDTO>
)

@Serializable
data class ItemDTO(
    val product_id: Int,
    val name: String,
    val quantity: Int,
    val price: Double
)