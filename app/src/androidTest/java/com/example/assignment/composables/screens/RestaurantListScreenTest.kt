package com.example.assignment.composables.screens

import com.example.assignment.fakes.FakeKtorClientImpl
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment.fakes.fakeRestaurant1
import com.example.assignment.fakes.fakeRestaurant2
import com.example.assignment.repositories.DataRepository
import com.example.assignment.repositories.NetworkRepository
import com.example.assignment.ui.theme.AssignmentTheme
import com.example.assignment.viewmodels.RestaurantListViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RestaurantListScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: RestaurantListViewModel

    @Before
    fun setUp() {
        viewModel = RestaurantListViewModel(
            networkRepository =  NetworkRepository(FakeKtorClientImpl()),
            dataRepository = DataRepository()
        )
    }

    @Test
    fun testRestaurantListScreen_listDisplayState() = runTest {
        composeTestRule.setContent {
            AssignmentTheme {
                RestaurantListScreen(onRestaurantSelected = {}, viewModel = viewModel)
            }
        }

        // Assert
        composeTestRule.onNodeWithText(fakeRestaurant1.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeRestaurant2.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeRestaurant1.getFormattedDeliveryTime())
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeRestaurant2.getFormattedDeliveryTime())
            .assertIsDisplayed()
    }
}
