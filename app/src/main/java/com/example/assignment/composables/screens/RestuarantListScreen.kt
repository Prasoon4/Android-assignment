package com.example.assignment.composables.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assignment.R
import com.example.assignment.composables.common.FilterBadge
import com.example.assignment.composables.common.LoadingState
import com.example.assignment.composables.common.RestaurantListItem
import com.example.assignment.viewmodels.RestaurantListViewModel
import com.example.network.models.Restaurant
import com.example.network.models.RestaurantFilter

sealed interface RestaurantListScreenViewState {
    data object Loading : RestaurantListScreenViewState
    data class ListDisplay(
        val restaurants: List<Restaurant>,
        val restaurantFilters: List<RestaurantFilter>
    ) : RestaurantListScreenViewState
    data class Error(val message: String) : RestaurantListScreenViewState
}

/**
 * Restaurant List Screen
 * @param onRestaurantSelected: callback to handle restaurant selection
 * @param viewModel: RestaurantListViewModel to fetch the restaurants
 */
@Composable
fun RestaurantListScreen(
    onRestaurantSelected: (Restaurant) -> Unit,
    viewModel: RestaurantListViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Column(modifier = Modifier.background(Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(8.dp)
                .height(56.dp)
                .width(56.dp)
        )

        when (val state = viewState) {
            RestaurantListScreenViewState.Loading -> LoadingState()
            is RestaurantListScreenViewState.Error -> {
                // Not handling the error properly, Just display the error message
                Text(text = state.message,
                    modifier = Modifier.padding(16.dp))
            }
            is RestaurantListScreenViewState.ListDisplay -> {

                val selectedFilterIds by viewModel.selectedFilterIds.collectAsState()
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .height(48.dp)
                ) {
                    items(state.restaurantFilters) { item ->
                        FilterBadge(
                            restaurantFilter = item,
                            isSelected = selectedFilterIds.contains(item.id),
                            toggleSelection = { viewModel.toggleFilter(item) })
                    }
                }

                LazyColumn {
                    items(
                        items = state.restaurants,
                        key = { it.id }
                    ) { restaurant ->
                        RestaurantListItem(
                            restaurant = restaurant,
                            onClick = { onRestaurantSelected(restaurant) })
                    }
                }
            }
        }
    }

}