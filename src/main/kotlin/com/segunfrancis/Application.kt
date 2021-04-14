package com.segunfrancis

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.segunfrancis.plugins.*
import io.ktor.locations.KtorExperimentalLocationsAPI

@KtorExperimentalLocationsAPI
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
