package com.example.bakeryapp.login.data

interface LoginRepository {
    suspend fun login(loginData: LoginRequest): LoginResponse
}