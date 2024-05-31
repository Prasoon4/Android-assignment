package com.example.assignment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.assignment.composables.screens.RestaurantDetailsViewState
import com.example.assignment.repositories.NetworkRepository
import com.example.network.ApiOperation
import com.example.network.models.RestaurantOpenStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class RestaurantDetailsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var networkRepository: NetworkRepository

    private lateinit var viewModel: RestaurantDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = RestaurantDetailsViewModel(networkRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        //testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun fetchRestaurantOpenStatus_setsStateToOpen_whenRestaurantIsOpen() = runTest {
        // Arrange
        val restaurantId = "123"
        `when`(networkRepository.fetchRestaurantOpenStatus(restaurantId))
            .thenReturn(
                ApiOperation.Success(RestaurantOpenStatus(restaurantId, true))
            )

        // Act
        viewModel.fetchRestaurantOpenStatus(restaurantId)

        // Assert
        val state = viewModel.stateFlow.first()
        Assert.assertEquals(RestaurantDetailsViewState.Open, state)
    }

    @Test
    fun fetchRestaurantOpenStatus_setsStateToClose_whenRestaurantIsClosed() = runTest {
        // Arrange
        val restaurantId = "123"
        `when`(networkRepository.fetchRestaurantOpenStatus(restaurantId))
            .thenReturn(
                ApiOperation.Success(RestaurantOpenStatus(restaurantId, false))
            )

        // Act
        viewModel.fetchRestaurantOpenStatus(restaurantId)

        // Assert
        val state = viewModel.stateFlow.first()
        Assert.assertEquals(RestaurantDetailsViewState.Close, state)
    }

    @Test
    fun fetchRestaurantOpenStatus_setsStateToUnknown_onFailure() = runTest {
        // Arrange
        val restaurantId = "123"
        `when`(networkRepository.fetchRestaurantOpenStatus(restaurantId))
            .thenReturn(
                ApiOperation.Failure(Exception())
            )

        // Act
        viewModel.fetchRestaurantOpenStatus(restaurantId)

        // Assert
        val state = viewModel.stateFlow.first()
        Assert.assertEquals(RestaurantDetailsViewState.Unknown, state)
    }
}
