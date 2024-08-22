package com.mostafatamer.postlibrary.uI_test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.mostafatamer.postlibrary.MainActivity
import com.mostafatamer.postlibrary.presentation.screens.navigatoin.MainNavigation
import com.mostafatamer.postlibrary.ui.theme.PostLibraryTheme
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

class GreetingScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testGreetingScreen() {
        // Arrange
        val name = "Compose"

        // Act
//        composeTestRule.setContent {
//
//        }

        // Assert
//        sleep(2000)
//        composeTestRule.onNodeWithText("Posts" ).assertIsDisplayed()
//        composeTestRule.onNodeWithText("Click Me").assertIsDisplayed().performClick()
    }
}
@Composable
fun Greeting2(name: String) {
    Text(text = "Hello, $name!")

    Button(
        onClick = {
            println("heeeeey")
        }
    ) {
        Text("Click Me")
    }
}