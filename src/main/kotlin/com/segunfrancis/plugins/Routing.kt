package com.segunfrancis.plugins

import com.segunfrancis.model.LoginRequest
import com.segunfrancis.model.LoginResponse
import com.segunfrancis.model.getList
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.response.*

@KtorExperimentalLocationsAPI
fun Application.configureRouting() {
    install(Locations) {
    }
    
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get<MyLocation> {
            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
        }
        // Register nested routes
        get<Type.Edit> {
            call.respondText("Inside $it")
        }
        get<Type.List> {
            call.respondText("Inside $it")
        }
        post<SendPost> {
            call.respondText("This is a test POST route")
        }
        post<SendPostParams> {
            val parameters = call.receiveParameters()
            val param1 = parameters["param1"] ?: "DEFAULT"
            val param2 = parameters["param2"] ?: "DEFAULT"
            call.respondText("This is a test POST route with parameters $param1 and $param2")
        }

        post("/login") {
            try {
                val loginRequest = call.receive<LoginRequest>()
                if (loginRequest.username == "admin" && loginRequest.password == "adminpw") {
                    call.respond(LoginResponse(true, "Login successful"))
                } else {
                    call.respond(LoginResponse(false, "Invalid credentials"))
                }
            } catch (e: Throwable) {
                //call.respondText("Error: ${e.localizedMessage}")
                call.respond(HttpStatusCode.NotFound, "Error: ${e.localizedMessage}")
            }
        }

        get<ListItems> {
            call.respond(HttpStatusCode.OK, getList())
        }

        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }
    }
}

@KtorExperimentalLocationsAPI
@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@KtorExperimentalLocationsAPI
@Location("/type/{name}")
data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}

@KtorExperimentalLocationsAPI
@Location("/test")
class SendPost

@KtorExperimentalLocationsAPI
@Location("/test2")
class SendPostParams

@KtorExperimentalLocationsAPI
@Location("/items")
class ListItems

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
