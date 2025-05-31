package com.example.bakeryapp.register.data

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val phone: String,
    val password: String,
)

@Serializable
data class RegisterResponse(
    val access_token: String,
    val token_type: String
)