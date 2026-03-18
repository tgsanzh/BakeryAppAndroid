package com.example.bakeryapp.start.data

import kotlinx.serialization.Serializable

@Serializable
data class StartRequest(
    val token: String,
)

@Serializable
data class StartResponse(
    val user_id: Int,
)