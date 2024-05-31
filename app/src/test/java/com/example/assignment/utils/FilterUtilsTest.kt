package com.example.assignment.utils

import com.example.network.models.Restaurant
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterUtilsTest {

    @Test
    fun test_getUniqueFilterIds_withEmptyList() {
        val restaurants = emptyList<Restaurant>()
        val result = getUniqueFilterIds(restaurants)
        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun test_getUniqueFilterIds_withSingleRestaurantAndNoFilters() {
        val restaurants = listOf(
            Restaurant(id = "1", name = "Restaurant 1", rating = 4.0f, filterIds = emptyList(), imageUrl = "", deliveryTimeMinutes = 30)
        )
        val result = getUniqueFilterIds(restaurants)
        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun test_getUniqueFilterIds_withSingleRestaurantAndFilters() {
        val restaurants = listOf(
            Restaurant(id = "1", name = "Restaurant 1", rating = 4.0f, filterIds = listOf("1", "2", "3"), imageUrl = "", deliveryTimeMinutes = 30)
        )
        val result = getUniqueFilterIds(restaurants)
        assertEquals(listOf("1", "2", "3"), result)
    }

    @Test
    fun test_getUniqueFilterIds_withMultipleRestaurantAndUniqueFilters() {
        val restaurants = listOf(
            Restaurant(id = "1", name = "Restaurant 1", rating = 4.0f, filterIds = listOf("1", "2"), imageUrl = "", deliveryTimeMinutes = 30),
            Restaurant(id = "2", name = "Restaurant 2", rating = 4.5f, filterIds = listOf("3", "4"), imageUrl = "", deliveryTimeMinutes = 40)
        )
        val result = getUniqueFilterIds(restaurants)
        assertEquals(listOf("1", "2", "3", "4"), result)
    }

    @Test
    fun test_getUniqueFilterIds_withMultipleRestaurantAndOverlappingFilters() {
        val restaurants = listOf(
            Restaurant(id = "1", name = "Restaurant 1", rating = 4.0f, filterIds = listOf("1", "2"), imageUrl = "", deliveryTimeMinutes = 30),
            Restaurant(id = "2", name = "Restaurant 2", rating = 4.5f, filterIds = listOf("2", "3"), imageUrl = "", deliveryTimeMinutes = 40),
            Restaurant(id = "3", name = "Restaurant 3", rating = 4.7f, filterIds = listOf("3", "4", "5"), imageUrl = "", deliveryTimeMinutes = 50)
        )
        val result = getUniqueFilterIds(restaurants)
        assertEquals(listOf("1", "2", "3", "4", "5"), result)
    }
}
