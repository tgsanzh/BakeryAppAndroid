package com.example.bakeryapp

import android.content.Context
import android.util.Log
import com.example.bakeryapp.utils.SharedPrefs
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.TimeUnit

fun privideClient(context: Context): HttpClient {
    val httpClient = HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(true)
                writeTimeout(30_000, TimeUnit.MILLISECONDS)
                readTimeout(30_000, TimeUnit.MILLISECONDS)
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging){
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("KTOR DATA ", message)
                }
            }
            level = LogLevel.ALL
        } 

        install(Auth) {
            bearer {
                loadTokens {
                    val token = SharedPrefs(context).getToken()
                    if (!token.isNullOrBlank()) {
                        BearerTokens(accessToken = token, refreshToken = "")
                    } else {
                        null
                    }
                }
            }
        }
    }
    return httpClient
}
