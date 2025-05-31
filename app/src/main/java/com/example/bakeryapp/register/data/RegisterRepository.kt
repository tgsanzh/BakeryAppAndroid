package com.example.bakeryapp.register.data

interface RegisterRepository {
    suspend fun register(registerData: RegisterRequest): RegisterResponse
}