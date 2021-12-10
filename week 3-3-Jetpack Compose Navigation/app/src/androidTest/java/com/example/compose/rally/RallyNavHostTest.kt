package com.example.compose.rally

import android.os.Build
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.filters.SdkSuppress
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SdkSuppress(minSdkVersion = Build.VERSION_CODES.S)
class RallyNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navHostController: NavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navHostController = rememberNavController()
            RallyNavHost(navController = navHostController)
        }
    }

    @Test
    fun rallyNavHost() {
        composeTestRule.onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToAllAccounts_viaUI() {
        composeTestRule.onNodeWithContentDescription("All Accounts")
            .performClick()

        composeTestRule.onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToBill_viaUI() {
        composeTestRule.onNodeWithContentDescription("All Bills").apply {
            performScrollTo()
            performClick()
        }

        val route= navHostController.currentBackStackEntry?.destination?.route
        assertEquals(route, "Bills")
    }

    @Test
    fun rallyNavHost_navigateToAllAccounts_callingNavigate() {
        runBlocking {
            withContext(Dispatchers.Main) {
                navHostController.navigate(RallyScreen.Accounts.name)
            }
        }
        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }
}