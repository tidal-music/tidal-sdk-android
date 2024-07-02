package com.tidal.sdk.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.tidal.sdk.demo.MainActivity

@Composable
fun StartScreen(navController: NavController) {
    val activity = LocalContext.current.findActivity() as MainActivity
    val isLoggedIn = remember { mutableStateOf(activity.credentialsProvider.isUserLoggedIn()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoggedIn.value) {
            LoggedInUI(
                onLogoutClicked = {
                    activity.logout()
                    isLoggedIn.value = activity.credentialsProvider.isUserLoggedIn()
                },
                onCatalogueClicked = { navController.navigate("catalogue") },
            )
        } else {
            NotLoggedInUI(
                { navController.navigate("login") },
            )
        }
    }
}

@Composable
private fun LoggedInUI(onLogoutClicked: () -> Unit, onCatalogueClicked: () -> Unit) {
    Column {
        Text(
            text = "You are logged in",
            color = Color.White,
        )
        Button(
            onClick = onCatalogueClicked,
        ) {
            Text(text = "Test catalogue")
        }
        Button(
            onClick = onLogoutClicked,
        ) {
            Text(text = "Log out")
        }
    }
}

@Composable
private fun NotLoggedInUI(onLoginClicked: () -> Unit) {
    Column {
        Text(
            text = "You are not logged in",
            color = Color.White,
        )
        Button(
            onClick = onLoginClicked,
        ) {
            Text(text = "Open Login Screen")
        }
    }
}
