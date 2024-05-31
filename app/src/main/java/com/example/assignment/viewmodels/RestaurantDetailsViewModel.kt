package com.example.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.repositories.NetworkRepository
import com.example.assignment.composables.screens.RestaurantDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow<RestaurantDetailsViewState>(
        value = RestaurantDetailsViewState.Unknown
    )
    val stateFlow = _viewState.asStateFlow()

    fun fetchRestaurantOpenStatus(restaurantId: String) = viewModelScope.launch {
        _viewState.update { return@update RestaurantDetailsViewState.Unknown }
        networkRepository.fetchRestaurantOpenStatus(restaurantId).onSuccess { response ->

            _viewState.update {
                return@update if (response.isCurrentlyOpen)
                    RestaurantDetailsViewState.Open
                else RestaurantDetailsViewState.Close
            }
        }.onFailure { _ ->
            _viewState.update {
                return@update RestaurantDetailsViewState.Unknown
            }
        }
    }
}