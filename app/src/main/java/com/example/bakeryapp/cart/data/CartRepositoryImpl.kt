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
    val client: HttpClient,
    val sharedPrefs: SharedPrefs
) : CartRepository {
    override suspend fun getCarts(): List<CartDTO> {
        val response: List<CartDTO> = client.get("${BASE_URL}/cart/").body<List<CartDTO>>()
        return response
    }

    override suspend fun plusToCart(cart: CartObject): Int {
        val data = CartQuantityChange(action = "plus")
        val response = client.put("${BASE_URL}/cart/${cart.id}/change-quantity") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.status.value
    }

    override suspend fun minusToCart(cart: CartObject): Int {
        val data = CartQuantityChange(action = "minus")
        val response = client.put("${BASE_URL}/cart/${cart.id}/change-quantity") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.status.value
    }

    override suspend fun order(address: String) {
        val data = OrderData(delivery_address = address)
        client.post("${BASE_URL}/orders/") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }

}