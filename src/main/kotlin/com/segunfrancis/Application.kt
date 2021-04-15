package com.segunfrancis

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.segunfrancis.plugins.*
import io.ktor.locations.KtorExperimentalLocationsAPI

@KtorExperimentalLocationsAPI
fun main() {
    val port = System.getenv("PORT") ?: "8080"
    embeddedServer(Netty, port = port.toInt(), host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
