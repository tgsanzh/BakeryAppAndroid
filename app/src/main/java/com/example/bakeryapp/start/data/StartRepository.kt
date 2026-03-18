package com.example.bakeryapp.start.data

interface StartRepository {
    suspend fun checkToken(data: StartRequest): Result<StartResponse>
}