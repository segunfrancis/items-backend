package com.segunfrancis.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val ok: Boolean,
    val message: String
)
