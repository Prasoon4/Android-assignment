package com.example.network

import com.example.network.models.RestaurantFilter
import com.example.network.models.RestaurantOpenStatus
import com.example.network.models.RestaurantResponse

interface KtorClient {
    suspend fun getRestaurants(): RestaurantResponse

    suspend fun getRestaurantOpenStatus(restaurantId: String): RestaurantOpenStatus

    suspend fun getRestaurantFilter(filterId: String): RestaurantFilter

    suspend fun getRestaurantFilters(filterIds: List<String>): List<RestaurantFilter>
}

