package com.example.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantResponse(val restaurants: List<Restaurant>)
