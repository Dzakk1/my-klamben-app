package com.example.myklamben

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.myklamben.model.FakeItemsDataSource
import com.example.myklamben.ui.navigation.Screen
import com.example.myklamben.ui.theme.MyKlambenTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MyKlambenAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController : TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyKlambenTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                MyKlambenApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("OrderItemList").performScrollToIndex(8)
        composeTestRule.onNodeWithText(FakeItemsDataSource.dummyItems[8].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailItem.route)
        composeTestRule.onNodeWithText(FakeItemsDataSource.dummyItems[8].title).assertIsDisplayed()
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("OrderItemList").performScrollToIndex(8)
        composeTestRule.onNodeWithText(FakeItemsDataSource.dummyItems[8].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailItem.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText(FakeItemsDataSource.dummyItems[1].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailItem.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_about).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}