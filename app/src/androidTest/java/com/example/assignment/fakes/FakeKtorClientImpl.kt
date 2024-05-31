package com.example.assignment.fakes

import com.example.network.KtorClient
import com.example.network.models.RestaurantFilter
import com.example.network.models.RestaurantOpenStatus
import com.example.network.models.RestaurantResponse

class FakeKtorClientImpl : KtorClient {
    override suspend fun getRestaurants(): RestaurantResponse {
        return RestaurantResponse(restaurants = listOf(fakeRestaurant1, fakeRestaurant2))
    }

    override suspend fun getRestaurantOpenStatus(restaurantId: String)
            : RestaurantOpenStatus {
        return RestaurantOpenStatus(
            restaurantId = restaurantId,
            isCurrentlyOpen = restaurantId == "1"
        )
    }

    override suspend fun getRestaurantFilter(filterId: String): RestaurantFilter {
        return RestaurantFilter(filterId, "Filter $filterId", "")
    }

    override suspend fun getRestaurantFilters(filterIds: List<String>)
            : List<RestaurantFilter> {
        return listOf(fakeRestaurantFilter1, fakeRestaurantFilter2)
    }
}