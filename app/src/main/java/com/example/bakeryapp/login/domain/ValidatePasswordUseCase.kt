package com.example.bakeryapp.login.domain

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean {
        return password.length >= 8
    }
}