package com.example.bakeryapp.cart.data

import com.example.bakeryapp.cart.presentation.CartObject
import com.example.bakeryapp.catalog.data.CartQuantityChange
import com.example.bakeryapp.catalog.data.CategoriesDTO
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
import io.ktor.http.headers
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CartRepositoryImpl(
    val client: HttpClient,
    val sharedPrefs: SharedPrefs
): CartRepository {
    override suspend fun getCarts(): List<CartDTO> {
        val response: List<CartDTO> = client.get("http://10.0.2.2:8080/cart/").body<List<CartDTO>>()
        return response
    }

    override suspend fun plusToCart(cart: CartObject): Int {
        val data = CartQuantityChange(action = "plus")
        val response = client.put("http://10.0.2.2:8080/cart/${cart.id}/change-quantity") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.status.value
    }

    override suspend fun minusToCart(cart: CartObject): Int {
        val data = CartQuantityChange(action = "minus")
        val response = client.put("http://10.0.2.2:8080/cart/${cart.id}/change-quantity") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.status.value
    }
    override suspend fun order(address: String) {
        val data = OrderData(delivery_address = address)
        client.post("http://10.0.2.2:8080/orders/") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }

}