package com.example.assignment.composables.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.assignment.ui.theme.FilterSelectedColor
import com.example.network.models.RestaurantFilter

/**
 * Composable function to display a filter text with an image.
 * @param restaurantFilter RestaurantFilter object representing the filter
 * @param isSelected boolean to determine if the filter is selected or not
 * @param toggleSelection function to toggle the selection state
 */
@Composable
fun FilterBadge(
    restaurantFilter: RestaurantFilter,
    isSelected: Boolean = false,
    toggleSelection: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .shadow(4.dp, shape = RoundedCornerShape(24.dp))
            .clickable { toggleSelection() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(48.dp)
                .background(color = if (isSelected) FilterSelectedColor else Color.White)
                .padding(start = 24.dp)
        ) {
            Text(
                text = restaurantFilter.name,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 32.dp, end = 16.dp)
            )
        }

        SubcomposeAsyncImage(
            model = restaurantFilter.imageUrl,
            contentDescription = restaurantFilter.name,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Preview
@Composable
fun FilterBadgePreview() {
    FilterBadge(
        restaurantFilter = RestaurantFilter("1", "Filter", ""),
        toggleSelection = {}
    )
}
