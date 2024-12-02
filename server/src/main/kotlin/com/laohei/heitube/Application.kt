package com.laohei.heitube

import com.laohei.heitube.domain.ApiResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ServerContentNegotiation

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    install(ServerContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
    }

    val client = HttpClient() {
        install(ContentNegotiation) {
            // 序列化
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/proxy") {
            val pn = call.queryParameters["pn"]
            // 代理到 Bilibili API
            val response = client.get("https://api.bilibili.com/x/web-interface/popular?ps=20&pn=$pn") {
                headers.append(HttpHeaders.UserAgent, "K/3.0")
            }

            val result = response.body<ApiResponse>()
            response.headers.forEach { key, values ->
                values.forEach { value ->
                    call.response.headers.append(key, value)
                }
            }
            call.respond(result)
        }
        get("/proxy-image") {
            val imageUrl = call.queryParameters["url"]
            println("url:$imageUrl")
            imageUrl?.let {
                val imageResponse = client.get(imageUrl) {
                    headers.append(HttpHeaders.UserAgent, "K/3.0")
                }
//                imageResponse.headers.forEach { key, values ->
//                    values.forEach { value ->
//                        call.response.headers.append(key, value)
//                    }
//                }
                call.respondBytes(imageResponse.bodyAsBytes())
            }

        }
    }
}