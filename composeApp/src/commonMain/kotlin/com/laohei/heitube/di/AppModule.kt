package com.laohei.heitube.di

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


object AppModule {

    val client = HttpClient {
        install(ContentNegotiation) {
            // 序列化
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        // 请求超时
        install(HttpTimeout) {
            socketTimeoutMillis = 20_000L
            requestTimeoutMillis = 20_000L
        }
    }

}