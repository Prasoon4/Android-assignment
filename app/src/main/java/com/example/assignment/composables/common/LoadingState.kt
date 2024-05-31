package com.example.assignment.composables.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment.utils.TEST_TAG_PROGRESS

/**
 * Composable function to Progress indicators with fillMaxSize Modifier
 */
@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.testTag(TEST_TAG_PROGRESS))
    }
}

@Preview
@Composable
fun LoadingStatePreview() {
    LoadingState()
}