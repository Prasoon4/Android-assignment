package com.example.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val rating: Float,
    val filterIds: List<String>,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("delivery_time_minutes") val deliveryTimeMinutes: Int
) {
    private var filterText: String = ""

    fun setFilterText(restaurantFilters: List<RestaurantFilter>) {
        val filterTextBuilder = StringBuilder()
        filterIds.forEach { filterId ->
            val filter = restaurantFilters.find { it.id == filterId }
            if (filter != null) {
                if (filterTextBuilder.isNotEmpty()) {
                    filterTextBuilder.append(" • ")
                }
                filterTextBuilder.append(filter.name)
            }
        }
        this.filterText = filterTextBuilder.toString()
    }

    fun getFilterText(): String {
        return filterText
    }

    fun getFormattedDeliveryTime(): String {
        val hour: Int = deliveryTimeMinutes / 60
        val minute = deliveryTimeMinutes % 60
        return StringBuilder().apply {
            if (hour > 0) {
                append("$hour hour${if (hour > 1) "s" else ""} ")
            }
            if (minute > 0) {
                append("$minute minute${if (minute > 1) "s" else ""}")
            }
        }.toString().trim()
    }
}
