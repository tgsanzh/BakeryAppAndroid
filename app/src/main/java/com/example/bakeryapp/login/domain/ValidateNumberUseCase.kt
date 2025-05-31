package com.example.bakeryapp.login.domain

class ValidateNumberUseCase {
    operator fun invoke(number: String): Boolean {
        return number.length == 10
    }
}