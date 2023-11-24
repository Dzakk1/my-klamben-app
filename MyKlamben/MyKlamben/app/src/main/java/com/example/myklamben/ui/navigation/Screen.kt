package com.example.myklamben.ui.navigation

sealed class Screen (val route : String) {
    object Home : Screen("Home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailItem : Screen("home/{shopItemId}") {
        fun createRoute(shopItemId : Long) = "home/$shopItemId"
    }
}

