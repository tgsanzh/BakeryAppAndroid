package com.example.bakeryapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefs(
    val context: Context
) {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE)

    fun setToken(token: String) {
        sharedPrefs.edit { putString("token", token) }
    }

    fun getToken(): String {
        return sharedPrefs.getString("token", "") ?: ""
    }
}