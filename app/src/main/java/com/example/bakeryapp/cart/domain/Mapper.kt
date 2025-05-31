package com.example.bakeryapp.cart.domain

import com.example.bakeryapp.cart.data.CartDTO

fun List<CartDTO>.toEntity(): List<CartObject> {
    return this.map {
        CartObject(
            id = it.cart_item_id,
            userId = it.user_id,
            quantity = it.quantity,
            short_product = ShortProduct(
                id = it.Products.product_id,
                title = it.Products.name,
                price = it.Products.price,
                imageUrl = it.Products.image_url,
            ),
        )
    }
}