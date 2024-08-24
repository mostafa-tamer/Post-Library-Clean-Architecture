package com.mostafatamer.postlibrary.uI_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mostafatamer.postlibrary.activity.MainActivity
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_correct_post_navigation() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("1")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("1")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(
                "Post 1 Details"
            ).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText(
            "Post 1 Details"
        ).assertIsDisplayed()
    }
}
