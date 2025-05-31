package com.example.bakeryapp.catalog.data

import android.util.Log
import com.example.bakeryapp.BASE_URL
import com.example.bakeryapp.utils.SharedPrefs
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent

class CatalogRepositoryImpl(
    val client: HttpClient,
    val sharedPrefs: SharedPrefs
) : CatalogRepository, KoinComponent {
    override suspend fun loadCatalog(): List<ProductsDTO> {
        val response: List<ProductsDTO> = client.get("${BASE_URL}/products/") {
        }.body<List<ProductsDTO>>()
        return response
    }

    override suspend fun loadCategories(): List<CategoriesDTO> {
        val response: List<CategoriesDTO> = client.get("${BASE_URL}/categories/") {
        }.body<List<CategoriesDTO>>()
        return response
    }

    override suspend fun toCart(product_id: Int) {
        val data = CartRequest(product_id = product_id)
        Log.d("TOKEN", sharedPrefs.getToken().toString())
        Log.d("ID", product_id.toString())
        client.post("${BASE_URL}/cart/") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }
}