package com.example.assignment.repositories

import com.example.assignment.utils.getUniqueFilterIds
import com.example.network.ApiOperation
import com.example.network.KtorClient
import com.example.network.models.Restaurant
import com.example.network.models.RestaurantFilter
import com.example.network.models.RestaurantOpenStatus
import com.example.network.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

/**
 * repository to handle restaurant data
 */
@Singleton
class NetworkRepository @Inject constructor(private val ktorClient: KtorClient) {

    /**
     * Fetch restaurant list and restaurant filters from server.
     * It also sets the filter text for each restaurant.
     */
    suspend fun fetchRestaurantData()
            : ApiOperation<Pair<List<Restaurant>, List<RestaurantFilter>>> {
        return safeApiCall {
            val restaurants = ktorClient.getRestaurants().restaurants
            val filterIds = getUniqueFilterIds(restaurants)
            val restaurantFilters = ktorClient.getRestaurantFilters(filterIds)
            restaurants.forEach { restaurant ->
                restaurant.setFilterText(restaurantFilters)
            }
            Pair(restaurants, restaurantFilters)
        }
    }

    suspend fun fetchRestaurantOpenStatus(restaurantId: String)
            : ApiOperation<RestaurantOpenStatus> {
        return safeApiCall {
            ktorClient.getRestaurantOpenStatus(restaurantId)
        }
    }
}