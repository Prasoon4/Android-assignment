package com.example.assignment.repositories

import com.example.network.models.Restaurant
import com.example.network.models.RestaurantFilter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * repository to handle restaurant data
 */
@Singleton
class DataRepository @Inject constructor() {

    private val restaurants = mutableListOf<Restaurant>()
    private val restaurantFilters = mutableListOf<RestaurantFilter>()

    fun saveRestaurants(restaurants: List<Restaurant>) {
        this.restaurants.clear()
        this.restaurants.addAll(restaurants)
    }

    fun getRestaurants(): List<Restaurant> {
        return restaurants
    }

    fun saveRestaurantFilters(restaurants: List<RestaurantFilter>) {
        this.restaurantFilters.clear()
        this.restaurantFilters.addAll(restaurants)
    }

    fun getRestaurantFilters(): List<RestaurantFilter> {
        return restaurantFilters
    }

    fun getRestaurant(restaurantId: String): Restaurant? {
        return restaurants.find { it.id == restaurantId }
    }
}