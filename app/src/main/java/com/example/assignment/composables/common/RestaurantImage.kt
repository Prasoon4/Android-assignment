package com.example.assignment.composables.common

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.example.assignment.utils.ImageAspectRatio
import com.example.network.models.Restaurant

/**
 * Composable to display a restaurant image.
 * @param restaurant The restaurant to display.
 */
@Composable
fun RestaurantImage(
    restaurant: Restaurant,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = restaurant.imageUrl,
        contentDescription = restaurant.name,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ImageAspectRatio),
        loading = { LoadingState() }
    )
}

@Preview(showBackground = true)
@Composable
private fun RestaurantImagePreview() {
    RestaurantImage(restaurant = Restaurant(
        id = "1",
        name = "Restaurant name",
        rating = 5f,
        imageUrl = "image url",
        filterIds = emptyList(),
        deliveryTimeMinutes = 30
    ))
}
