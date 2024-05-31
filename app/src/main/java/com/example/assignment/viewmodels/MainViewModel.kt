package com.example.assignment.viewmodels

import androidx.lifecycle.ViewModel
import com.example.assignment.repositories.DataRepository
import com.example.network.models.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun getRestaurant(restaurantId: String): Restaurant? {
        return dataRepository.getRestaurant(restaurantId)
    }
}
