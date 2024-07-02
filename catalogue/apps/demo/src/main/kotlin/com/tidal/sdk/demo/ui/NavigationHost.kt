package com.tidal.sdk.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tidal.sdk.demo.ui.catalogue.CatalogueTestScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "start",
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("start") {
            StartScreen(navController)
        }
        composable("login") {
            LoginScreen()
        }
        composable("catalogue") {
            CatalogueTestScreen()
        }
    }
}
