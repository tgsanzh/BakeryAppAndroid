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
    val client: HttpClient
) : CatalogRepository, KoinComponent {
    override suspend fun loadCatalog(): Result<List<ProductsDTO>> {
        return runCatching {
            client.get("${BASE_URL}/products/").body<List<ProductsDTO>>()
        }
    }

    override suspend fun loadCategories(): Result<List<CategoriesDTO>> {
        return runCatching {
            client.get("${BASE_URL}/categories/").body<List<CategoriesDTO>>()
        }
    }

    override suspend fun toCart(product_id: Int): Result<Int> {
        val data = CartRequest(product_id = product_id)
        return runCatching {
            client.post("${BASE_URL}/cart/") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.status.value
        }
    }
}