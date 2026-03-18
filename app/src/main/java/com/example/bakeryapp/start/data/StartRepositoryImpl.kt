package com.example.bakeryapp.start.data

import com.example.bakeryapp.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent

class StartRepositoryImpl(
    private val client: HttpClient
) : StartRepository, KoinComponent {
    override suspend fun checkToken(data: StartRequest): Result<StartResponse> {
        return runCatching {
            client.get("${BASE_URL}/users/protected").body<StartResponse>()
        }
    }

}