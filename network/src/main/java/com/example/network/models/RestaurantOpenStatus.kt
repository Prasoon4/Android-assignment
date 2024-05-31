package com.example.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantOpenStatus(@SerialName("restaurant_id") val restaurantId: String,
                                @SerialName("is_currently_open") val isCurrentlyOpen: Boolean)
