package com.example.bakeryapp.register.data

import com.example.bakeryapp.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent

class RegisterRepositoryImpl(
    private val client: HttpClient
) : RegisterRepository, KoinComponent {
    override suspend fun register(registerData: RegisterRequest): RegisterResponse {
        val response: RegisterResponse = client.post("${BASE_URL}/users/register") {
            contentType(ContentType.Application.Json)
            setBody(registerData)
        }.body<RegisterResponse>()
        return response
    }
}