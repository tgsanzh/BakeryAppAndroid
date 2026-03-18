package com.example.bakeryapp.login.data

import com.example.bakeryapp.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent

class LoginRepositoryImpl(
    private val client: HttpClient
) : LoginRepository, KoinComponent {
    override suspend fun login(loginData: LoginRequest): Result<LoginResponse> {
        return runCatching {
            client.post("${BASE_URL}/users/login") {
                contentType(ContentType.Application.Json)
                setBody(loginData)
            }.body<LoginResponse>()
        }
    }
}