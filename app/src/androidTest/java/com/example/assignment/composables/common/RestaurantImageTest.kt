package com.example.assignment.composables.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment.composables.common.RestaurantImage
import com.example.network.models.Restaurant
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun restaurantImage_displaysCorrectly() {
        val restaurant = Restaurant(
            "1", "test name", 5f, emptyList(),
            "",
            30
        )

        composeTestRule.setContent {
            RestaurantImage(restaurant = restaurant)
        }

        composeTestRule
            .onNodeWithContentDescription("test name")
            .assertExists()
            .assertIsDisplayed()
    }
}
