package com.example.assignment.composables.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.assignment.composables.common.FilterBadge
import com.example.assignment.ui.theme.AssignmentTheme
import com.example.network.models.RestaurantFilter
import org.junit.Rule
import org.junit.Test

class FilterBadgeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun filterBadge_isDisplayed() {
        val restaurantFilter = RestaurantFilter(id = "1", name = "test", imageUrl = "test_url")

        composeTestRule.setContent {
            AssignmentTheme {
                FilterBadge(restaurantFilter = restaurantFilter, toggleSelection = {})
            }
        }

        composeTestRule.onNodeWithText("test").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("test").assertIsDisplayed()
    }

    @Test
    fun filterBadge_toggleSelection() {
        val restaurantFilter = RestaurantFilter(id = "1", name = "test", imageUrl = "test_url")
        var isSelected = false

        composeTestRule.setContent {
            AssignmentTheme {
                FilterBadge(
                    restaurantFilter = restaurantFilter,
                    isSelected = isSelected,
                    toggleSelection = { isSelected = !isSelected }
                )
            }
        }

        composeTestRule.onNodeWithText("test").performClick()
        assert(isSelected)
    }
}
