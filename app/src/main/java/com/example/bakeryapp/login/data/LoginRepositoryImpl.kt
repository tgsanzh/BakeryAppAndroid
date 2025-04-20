package com.example.bakeryapp.login.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginRepositoryImpl(
    private val client: HttpClient
): LoginRepository, KoinComponent {
    override suspend fun login(loginData: LoginRequest): LoginResponse {
        val response: LoginResponse = client.post("http://10.0.2.2:8080/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginData)
        }.body<LoginResponse>()
        return response
    }
}