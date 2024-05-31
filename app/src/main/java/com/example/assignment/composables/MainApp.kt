package com.example.assignment.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment.composables.screens.RestaurantDetailsScreen
import com.example.assignment.composables.screens.RestaurantListScreen
import com.example.assignment.viewmodels.MainViewModel

/**
 * App start point
 */
@Composable
fun MainApp() {
    val viewModel: MainViewModel = hiltViewModel()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen") {
        composable(route = "home_screen") {
            RestaurantListScreen(onRestaurantSelected = { restaurant ->
                navController.navigate("restaurant_details/${restaurant.id}")
            })
        }
        composable(
            route = "restaurant_details/{restaurantId}",
            arguments = listOf(navArgument("restaurantId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val restaurantId: String =
                backStackEntry.arguments?.getString("restaurantId") ?: ""
            val restaurant = viewModel.getRestaurant(restaurantId)
                ?: // Handle the case where the restaurant is not found
                return@composable
            RestaurantDetailsScreen(restaurant = restaurant)
        }
    }
}