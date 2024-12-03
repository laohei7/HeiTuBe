package com.laohei.heitube

import com.laohei.heitube.domain.ApiResponse
import com.laohei.heitube.domain.Hots
import com.laohei.heitube.domain.VideoInfo
import io.ktor.client.*
import io.ktor.client.call.*
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
            val response = client.get("https://api.bilibili.com/x/web-interface/popular?ps=20&pn=$pn") {
                headers.append(HttpHeaders.UserAgent, "K/3.0")
            }
            val result = response.body<ApiResponse<Hots>>()
            response.headers.forEach { key, values ->
                values.forEach { value ->
                    call.response.headers.append(key, value)
                }
            }
            call.respond(result)
        }
        get("proxy-video") {
            val avid = call.queryParameters["avid"]
            val bvid = call.queryParameters["bvid"]
            val cid = call.queryParameters["cid"]
            val qn = call.queryParameters["qn"] ?: "80"
            val response =
                client.get(
                    "https://api.bilibili.com/x/player/wbi/playurl?avid=$avid&bvid=$bvid&cid=$cid&qn=0&fnver=0&fnval=4048&fourk=1&gaia_source=&from_client=BROWSER&is_main_page=true&need_fragment=false&isGaiaAvoided=false&session=33e1a32c776a41aa79589202b6de7c07&voice_balance=1&web_location=1315873&dm_img_list=[]&dm_img_str=V2ViR0wgMS4wIChPcGVuR0wgRVMgMi4wIENocm9taXVtKQ&dm_cover_img_str=QU5HTEUgKE5WSURJQSBDb3Jwb3JhdGlvbiwgTlZJRElBIEdlRm9yY2UgR1RYIDEwNTAgVGkvUENJZS9TU0UyLCBPcGVuR0wgNC41LjApR29vZ2xlIEluYy4gKE5WSURJQSBDb3Jwb3JhdGlvbi&dm_img_inter=%7B%22ds%22:[],%22wh%22:[4607,4634,61],%22of%22:[28,56,28]%7D&w_rid=f0bf7527e9879001d7a0e4491910d6d8&wts=1733160345"
                ) {
                    headers.append(HttpHeaders.UserAgent, "K/3.0")
                }
            val result = response.body<ApiResponse<VideoInfo>>()
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
                call.respondBytes(imageResponse.bodyAsBytes())
            }

        }
    }
}