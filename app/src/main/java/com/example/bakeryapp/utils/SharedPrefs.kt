package com.example.bakeryapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(
    val context: Context
) {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE)

    fun setToken(token: String) {
        sharedPrefs.edit().putString("token", token).apply()
    }
    fun getToken(): String? {
        return sharedPrefs.getString("token", "")
    }
}