package com.example.bakeryapp.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class ToastManager(
    private val context: Context
) {
    private val appContext = context.applicationContext
    private val mainHandler = Handler(Looper.getMainLooper())
    private var currentToast: Toast? = null

    fun show(message: String, duration: Int = Toast.LENGTH_SHORT) {
        mainHandler.post {
            currentToast?.cancel()
            currentToast = Toast.makeText(appContext, message, duration)
            currentToast?.show()
        }
    }
}