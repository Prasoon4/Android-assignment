package com.example.assignment.utils

import com.example.network.models.Restaurant

/**
 * Returns a list of unique filter ids from a list of restaurants' filter ids.
 * @param restaurants A list of restaurants.
 * @return A list of unique filter ids.
 */
fun getUniqueFilterIds(restaurants: List<Restaurant>): List<String> {
    val uniqueIds = mutableListOf<String>()
    restaurants.forEach { restaurant ->
        restaurant.filterIds.forEach {
            if (!uniqueIds.contains(it)) {
                uniqueIds.add(it)
            }
        }
    }
    return uniqueIds
}