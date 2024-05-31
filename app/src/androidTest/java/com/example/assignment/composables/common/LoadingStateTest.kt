package com.example.assignment.composables.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.assignment.utils.TEST_TAG_PROGRESS
import org.junit.Rule
import org.junit.Test

class LoadingStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_displaysCircularProgressIndicator() {
        composeTestRule.setContent {
            LoadingState()
        }

        composeTestRule.onNodeWithTag(TEST_TAG_PROGRESS).assertIsDisplayed()
    }
}