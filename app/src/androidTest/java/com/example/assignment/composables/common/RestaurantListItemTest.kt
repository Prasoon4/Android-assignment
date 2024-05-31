package com.example.assignment.composables.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment.composables.common.RestaurantListItem
import com.example.assignment.ui.theme.AssignmentTheme
import com.example.network.models.Restaurant
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantListItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun restaurantListItem_displaysCorrectly() {
        // Given a restaurant
        val restaurant = Restaurant(
            id = "1",
            name = "Restaurant name",
            rating = 4.5f,
            imageUrl = "image url",
            filterIds = emptyList(),
            deliveryTimeMinutes = 30
        )

        // When the composable is set
        composeTestRule.setContent {
            AssignmentTheme {
                RestaurantListItem(
                    restaurant = restaurant,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Then the restaurant details should be displayed correctly
        composeTestRule.onNodeWithText(restaurant.name)
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("4.5")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(restaurant.getFormattedDeliveryTime())
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun restaurantListItem_clickable() {
        // Given a restaurant and a click action
        val restaurant = Restaurant(
            id = "1",
            name = "Restaurant name",
            rating = 5f,
            imageUrl = "image url",
            filterIds = emptyList(),
            deliveryTimeMinutes = 30
        )
        var clicked = false
        val onClick = { clicked = true }

        // When the composable is set
        composeTestRule.setContent {
            AssignmentTheme {
                RestaurantListItem(
                    restaurant = restaurant,
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Then the click action should be triggered
        composeTestRule.onNodeWithText(restaurant.name)
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        composeTestRule.runOnIdle {
            assert(clicked)
        }
    }
}
