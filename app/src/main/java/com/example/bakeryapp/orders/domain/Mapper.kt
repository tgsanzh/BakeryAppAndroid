package com.example.bakeryapp.orders.domain

import com.example.bakeryapp.orders.data.OrderDTO

fun List<OrderDTO>.toEntity(): List<Orders> {
    return this.map {
        Orders(
            orderId = it.order_id,
            status = it.status,
            totalAmount = it.total_amount,
            deliveryAddress = it.delivery_address,
            createdAt = it.created_at,
            items = it.items.map { item ->
                OrdersItems(
                    productId = item.product_id,
                    productName = item.name,
                    quantity = item.quantity,
                    pricePerItem = item.price,
                )
            },
        )
    }
}
