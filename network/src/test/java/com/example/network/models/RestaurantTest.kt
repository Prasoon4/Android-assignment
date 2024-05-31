package com.example.network.models

import org.junit.Assert.assertEquals
import org.junit.Test

class RestaurantTest {

    private val restaurant = Restaurant(
        id = "1",
        name = "Restaurant name",
        rating = 5f,
        imageUrl = "image url",
        filterIds = emptyList(),
        deliveryTimeMinutes = 30
    )

    private val filters = listOf(
        RestaurantFilter("1", "Filter 1", ""),
        RestaurantFilter("2", "Filter 2", "")
    )

    @Test
    fun testSetFilterText_withEmptyFilter() {
        restaurant.setFilterText(filters)
        assertEquals("", restaurant.getFilterText())
    }

    @Test
    fun testSetFilterText_withOneFilter() {
        val restaurant2 = restaurant.copy(filterIds = listOf("2"))
        restaurant2.setFilterText(filters)

        assertEquals("Filter 2", restaurant2.getFilterText())
    }

    @Test
    fun testSetFilterText_withMissingFilter() {
        val restaurant2 = restaurant.copy(filterIds = listOf("1", "2", "3"))
        restaurant2.setFilterText(filters)

        assertEquals("Filter 1 • Filter 2", restaurant2.getFilterText())
    }

    @Test
    fun testGetFormattedDeliveryTime() {
        val restaurant2 = restaurant.copy(deliveryTimeMinutes = 75)
        assertEquals("1 hour 15 minutes", restaurant2.getFormattedDeliveryTime())
    }

    @Test
    fun testGetFormattedDeliveryTime_exactHour() {
        val restaurant2 = restaurant.copy(deliveryTimeMinutes = 60)
        assertEquals("1 hour", restaurant2.getFormattedDeliveryTime())
    }

    @Test
    fun testGetFormattedDeliveryTime_exactMinute() {
        val restaurant2 = restaurant.copy(deliveryTimeMinutes = 1)
        assertEquals("1 minute", restaurant2.getFormattedDeliveryTime())
    }

    @Test
    fun testGetFormattedDeliveryTime_minutesOnly() {
        assertEquals("30 minutes", restaurant.getFormattedDeliveryTime())
    }
}
