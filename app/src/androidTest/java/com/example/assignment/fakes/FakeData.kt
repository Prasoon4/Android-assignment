package com.example.assignment.fakes

import com.example.network.models.Restaurant
import com.example.network.models.RestaurantFilter

val fakeRestaurantFilter1 = RestaurantFilter(
    id = "1",
    name = "Filter 1",
    imageUrl = "image url"
)

val fakeRestaurantFilter2 = RestaurantFilter(
    id = "2",
    name = "Filter 2",
    imageUrl = "image url"
)

val fakeRestaurant1 = Restaurant(
    id = "1",
    name = "Restaurant 1",
    rating = 5f,
    imageUrl = "image url",
    filterIds = listOf("1"),
    deliveryTimeMinutes = 30
).apply {
    setFilterText(listOf(fakeRestaurantFilter1))
}
val fakeRestaurant2: Restaurant = Restaurant(
    id = "2",
    name = "Restaurant 2",
    rating = 5f,
    imageUrl = "image url",
    filterIds = listOf("2"),
    deliveryTimeMinutes = 45
).apply {
    setFilterText(listOf(fakeRestaurantFilter2))
}

