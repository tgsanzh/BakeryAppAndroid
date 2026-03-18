package com.example.bakeryapp.cart.data

import com.example.bakeryapp.BASE_URL
import com.example.bakeryapp.cart.domain.CartObject
import com.example.bakeryapp.catalog.data.CartQuantityChange
import com.example.bakeryapp.catalog.data.OrderData
import com.example.bakeryapp.utils.SharedPrefs
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CartRepositoryImpl(
    val client: HttpClient
) : CartRepository {
    override suspend fun getCarts(): Result<List<CartDTO>> {
        return runCatching { client.get("${BASE_URL}/cart/").body<List<CartDTO>>() }
    }

    override suspend fun plusToCart(cart: CartObject): Result<Int> {
        val data = CartQuantityChange(action = "plus")
        return runCatching {
            client.put("${BASE_URL}/cart/${cart.id}/change-quantity") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.status.value
        }
    }

    override suspend fun minusToCart(cart: CartObject): Result<Int> {
        val data = CartQuantityChange(action = "minus")
        return runCatching {
            client.put("${BASE_URL}/cart/${cart.id}/change-quantity") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.status.value
        }
    }

    override suspend fun order(address: String): Result<Int> {
        val data = OrderData(delivery_address = address)
        return runCatching {
            client.post("${BASE_URL}/orders/") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.status.value
        }
    }

}