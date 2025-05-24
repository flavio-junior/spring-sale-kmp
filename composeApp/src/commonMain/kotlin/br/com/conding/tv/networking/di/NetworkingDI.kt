package br.com.conding.tv.networking.di

import br.com.conding.tv.networking.resources.NetworkingUtils.TIMEOUT_SIZE
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(plugin = ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true })
            }
            install(plugin = Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(plugin = DefaultRequest) {
                url {
                    protocol = io.ktor.http.URLProtocol.HTTP
                    host = "192.168.1.100"
                    port = 8002
                }
                contentType(type = ContentType.Application.Json)
            }
            install(plugin = HttpTimeout) {
                requestTimeoutMillis = TIMEOUT_SIZE
                connectTimeoutMillis = TIMEOUT_SIZE
                socketTimeoutMillis = TIMEOUT_SIZE
            }
        }
    }
}

