package com.example.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.composables.screens.RestaurantListScreenViewState
import com.example.assignment.repositories.DataRepository
import com.example.assignment.repositories.NetworkRepository
import com.example.network.models.Restaurant
import com.example.network.models.RestaurantFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dataRepository: DataRepository
) : ViewModel() {
    private val _viewState =
        MutableStateFlow<RestaurantListScreenViewState>(RestaurantListScreenViewState.Loading)
    val viewState: StateFlow<RestaurantListScreenViewState> = _viewState.asStateFlow()

    private val _selectedFilterIds = MutableStateFlow<List<String>>(emptyList())
    val selectedFilterIds: StateFlow<List<String>> = _selectedFilterIds.asStateFlow()

    init {
        fetchRestaurants()
    }

    fun fetchRestaurants() = viewModelScope.launch {
        networkRepository.fetchRestaurantData().onSuccess { response ->
            dataRepository.saveRestaurants(response.first)
            dataRepository.saveRestaurantFilters(response.second)

            _viewState.update {
                return@update RestaurantListScreenViewState
                    .ListDisplay(restaurants = response.first, response.second)
            }
        }.onFailure { exception ->
            _viewState.update {
                return@update RestaurantListScreenViewState.Error(exception.message ?: "Error")
            }
        }
    }

    fun toggleFilter(restaurantFilter: RestaurantFilter) {
        if (_selectedFilterIds.value.contains(restaurantFilter.id)) {
            _selectedFilterIds.update { filterIds ->
                filterIds.filter { it != restaurantFilter.id }
            }
        } else {
            _selectedFilterIds.update {
                it + restaurantFilter.id
            }
        }
        _viewState.update {
            return@update RestaurantListScreenViewState
                .ListDisplay(
                    restaurants = getFilteredRestaurants(),
                    dataRepository.getRestaurantFilters()
                )
        }
    }

    private fun getFilteredRestaurants(
        restaurants: List<Restaurant>
        = dataRepository.getRestaurants()
    ): List<Restaurant> {
        return restaurants.filter { restaurant ->
            _selectedFilterIds.value.isEmpty()
                    || _selectedFilterIds.value.any { it in restaurant.filterIds }
        }
    }
}