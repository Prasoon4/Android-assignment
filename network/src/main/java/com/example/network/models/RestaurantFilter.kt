package com.example.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantFilter(val id: String,
                            val name: String,
                            @SerialName("image_url") val imageUrl: String)