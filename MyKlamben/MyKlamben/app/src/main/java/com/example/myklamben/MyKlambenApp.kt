package com.example.myklamben

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myklamben.ui.navigation.NavigationItem
import com.example.myklamben.ui.navigation.Screen
import com.example.myklamben.ui.theme.MyKlambenTheme
import com.example.myklamben.ui.view.about.AboutView
import com.example.myklamben.ui.view.cart.CartView
import com.example.myklamben.ui.view.detail.DetailView
import com.example.myklamben.ui.view.home.HomeView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyKlambenApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailItem.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Home.route) {
                HomeView(
                    navigateToDetail = { rewardId ->
                        navController.navigate(Screen.DetailItem.createRoute(rewardId))
                    }
                )
            }
            composable(Screen.Cart.route) {
                val context = LocalContext.current
                CartView(
                    onOrderButtonClicked = { message ->
                        shareOrder(context, message)
                    }
                )
            }
            composable(Screen.Profile.route) {
                AboutView()
            }
            composable(
                route = Screen.DetailItem.route,
                arguments = listOf(navArgument("shopItemId") { type = NavType.LongType }),
            )  {
                val id = it.arguments?.getLong("shopItemId") ?: -1L
                DetailView(
                    itemId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun BottomBar(
    navController : NavHostController,
    modifier : Modifier = Modifier
) {
    NavigationBar (
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            
            NavigationItem(
                title = stringResource(R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            
            NavigationItem(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen =  Screen.Profile
            ),
            
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = item.icon,
                        contentDescription = item.title )
                },
                label = { Text(item.title)},
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

private fun shareOrder(context : Context, summary : String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.klamben_app))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.klamben_app)
        )
    )
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun MyKlambenAppPreview() {
    MyKlambenTheme {
        MyKlambenApp()
    }

}