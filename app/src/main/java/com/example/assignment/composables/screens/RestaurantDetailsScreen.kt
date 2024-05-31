package com.example.assignment.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assignment.R
import com.example.assignment.composables.common.RestaurantImage
import com.example.assignment.utils.ImageAspectRatio
import com.example.assignment.ui.theme.*
import com.example.assignment.viewmodels.RestaurantDetailsViewModel
import com.example.network.models.Restaurant

sealed interface RestaurantDetailsViewState {
    data object Unknown : RestaurantDetailsViewState // Initial state or error state
    data object Open : RestaurantDetailsViewState
    data object Close : RestaurantDetailsViewState
}

/**
 * Restaurant details screen
 * @param restaurant Restaurant to display
 * @param viewModel ViewModel to fetch restaurant open status
 */
@Composable
fun RestaurantDetailsScreen(
    restaurant: Restaurant,
    viewModel: RestaurantDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(restaurant.id) {
        viewModel.fetchRestaurantOpenStatus(restaurant.id)
    }

    val state by viewModel.stateFlow.collectAsState()

    val configuration = LocalConfiguration.current
    val cardTopMargin = configuration.screenWidthDp.dp / ImageAspectRatio - 64.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        RestaurantImage(restaurant)

        // Restaurant details card
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .padding(top = cardTopMargin)
                .fillMaxWidth()
                .shadow(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 24.sp,
                    color = TitleColor
                )
                Text(
                    text = restaurant.getFilterText(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = SubTitleColor
                )

                val statusText = when (state) {
                    RestaurantDetailsViewState.Unknown -> ""
                    RestaurantDetailsViewState.Open -> stringResource(id = R.string.open)
                    RestaurantDetailsViewState.Close -> stringResource(id = R.string.close)
                }

                Text(
                    text = statusText,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = if (state == RestaurantDetailsViewState.Open) OpenColor else CloseColor,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantDetailScreenPreview() {
    AssignmentTheme {
        RestaurantDetailsScreen(
            restaurant = Restaurant(
                id = "1",
                name = "Restaurant name",
                rating = 5f,
                imageUrl = "image url",
                filterIds = emptyList(),
                deliveryTimeMinutes = 30
            )
        )
    }
}