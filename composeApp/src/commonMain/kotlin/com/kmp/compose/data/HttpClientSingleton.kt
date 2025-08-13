package com.kmp.compose.data

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object HttpClientSingleton {
    val client = HttpClient {
        install(ContentNegotiation) {
            // TODO Fix API so it serves application/json
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}
