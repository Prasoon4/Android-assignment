package com.example.assignment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.assignment.composables.screens.RestaurantListScreenViewState
import com.example.assignment.repositories.DataRepository
import com.example.assignment.repositories.NetworkRepository
import com.example.network.ApiOperation
import com.example.network.models.Restaurant
import com.example.network.models.RestaurantFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var networkRepository: NetworkRepository

    private lateinit var viewModel: RestaurantListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = RestaurantListViewModel(networkRepository, DataRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchRestaurants_setsViewStateToListDisplay_onSuccess() = runTest {
        // Arrange
        val restaurants = listOf(
            Restaurant(id = "1", name = "Restaurant 1", rating = 4.0f, filterIds = listOf("1"), imageUrl = "", deliveryTimeMinutes = 30),
            Restaurant(id = "2", name = "Restaurant 2", rating = 4.0f, filterIds = listOf("2"), imageUrl = "", deliveryTimeMinutes = 30)
        )
        val filters = listOf(
            RestaurantFilter(id = "1", name = "Filter 1", imageUrl = ""),
            RestaurantFilter(id = "2", name = "Filter 2", imageUrl = "")
        )

        `when`(networkRepository.fetchRestaurantData())
            .thenReturn(ApiOperation.Success(Pair(restaurants, filters)))

        // Act
        viewModel.fetchRestaurants()
        advanceUntilIdle() // Wait for coroutines to complete

        // Assert
        val state = viewModel.viewState.first()
        Assert.assertTrue(state is RestaurantListScreenViewState.ListDisplay)
        val listState = state as RestaurantListScreenViewState.ListDisplay
        Assert.assertEquals(restaurants.size, listState.restaurants.size)
        Assert.assertEquals(filters.size, listState.restaurantFilters.size)
    }

    @Test
    fun fetchRestaurants_setsViewStateToError_onFailure() = runTest {
        // Arrange
        val errorMessage = "Error fetching restaurants"
        `when`(networkRepository.fetchRestaurantData())
            .thenReturn(ApiOperation.Failure(Exception(errorMessage)))

        // Act
        viewModel.fetchRestaurants()
        advanceUntilIdle() // Wait for coroutines to complete

        // Assert
        val state = viewModel.viewState.first()
        Assert.assertTrue(state is RestaurantListScreenViewState.Error)
        val errorState = state as RestaurantListScreenViewState.Error
        Assert.assertEquals(errorMessage, errorState.message)
    }

    @Test
    fun toggleFilter_addsAndRemovesFilterFromSelectedFilterIds() = runTest {
        // Arrange
        val filter = RestaurantFilter(id = "1", name = "Filter 1", imageUrl = "")

        // Act
        viewModel.toggleFilter(filter)
        advanceUntilIdle() // Wait for coroutines to complete

        // Assert
        var selectedFilterIds = viewModel.selectedFilterIds.first()
        Assert.assertTrue(selectedFilterIds.contains(filter.id))

        // Act
        viewModel.toggleFilter(filter)
        advanceUntilIdle() // Wait for coroutines to complete

        // Assert
        selectedFilterIds = viewModel.selectedFilterIds.first()
        Assert.assertFalse(selectedFilterIds.contains(filter.id))
    }
}
