package br.com.conding.tv.features.home.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.conding.tv.navigation.AppDestinations

@Composable
actual fun HomeScreen(
    navGraph: NavHostController,
    goToNextScreen: (AppDestinations) -> Unit,
    goToLoginScreen: () -> Unit
) {
    MobileHomeScreen(navGraph, goToNextScreen, goToLoginScreen)
}
