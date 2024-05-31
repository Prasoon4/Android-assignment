package com.example.assignment.composables.screens

import com.example.assignment.fakes.FakeKtorClientImpl
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.assignment.R
import com.example.assignment.fakes.fakeRestaurant1
import com.example.assignment.fakes.fakeRestaurant2
import com.example.assignment.repositories.NetworkRepository
import com.example.assignment.ui.theme.AssignmentTheme
import com.example.assignment.viewmodels.RestaurantDetailsViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RestaurantDetailsScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext


    private lateinit var viewModel: RestaurantDetailsViewModel

    @Before
    fun setUp() {
        viewModel = RestaurantDetailsViewModel(NetworkRepository(FakeKtorClientImpl()))
    }

    @Test
    fun testRestaurantDetailsScreen_openStatus() = runTest {
        composeTestRule.setContent {
            AssignmentTheme {
                RestaurantDetailsScreen(restaurant = fakeRestaurant1, viewModel = viewModel)
            }
        }

        // Assert
        composeTestRule.onNodeWithText(fakeRestaurant1.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeRestaurant1.getFilterText()).assertIsDisplayed()
        composeTestRule.onNodeWithText(appContext.getString(R.string.open)).assertIsDisplayed()
    }

    @Test
    fun testRestaurantDetailsScreen_closeStatus() = runTest {
        composeTestRule.setContent {
            AssignmentTheme {
                RestaurantDetailsScreen(restaurant = fakeRestaurant2, viewModel = viewModel)
            }
        }

        // Assert
        composeTestRule.onNodeWithText(fakeRestaurant2.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeRestaurant2.getFilterText()).assertIsDisplayed()
        composeTestRule.onNodeWithText(appContext.getString(R.string.close)).assertIsDisplayed()
    }

    @Test
    fun testRestaurantDetailsScreen_unknownStatus() = runTest {
        composeTestRule.setContent {
            AssignmentTheme {
                RestaurantDetailsScreen(restaurant = fakeRestaurant2, viewModel = viewModel)
            }
        }

        // Assert
        composeTestRule.onNodeWithText(fakeRestaurant2.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeRestaurant2.getFilterText()).assertIsDisplayed()
        composeTestRule.onNodeWithText("").assertDoesNotExist()
    }
}
