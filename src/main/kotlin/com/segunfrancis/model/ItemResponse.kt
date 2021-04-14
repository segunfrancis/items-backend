package com.segunfrancis.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemResponse(val name: String)

fun getList(): List<ItemResponse> {
    return listOf(
        ItemResponse("Mango"),
        ItemResponse("Orange"),
        ItemResponse("Lemon"),
        ItemResponse("Apple"),
        ItemResponse("Lime"),
        ItemResponse("Pineapple"),
        ItemResponse("Watermelon"),
        ItemResponse("Cucumber"),
        ItemResponse("Grape"),
        ItemResponse("Banana"),
    )
}