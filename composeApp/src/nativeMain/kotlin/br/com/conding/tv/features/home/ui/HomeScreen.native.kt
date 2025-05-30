package br.com.conding.tv.features.home.ui

import androidx.compose.runtime.Composable
import br.com.conding.tv.components.ui.BottomNavigation
import br.com.conding.tv.navigation.AppDestinations

@Composable
actual fun HomeScreen(
    goToNextScreen: (AppDestinations) -> Unit,
    goToLoginScreen: () -> Unit
) {
    BottomNavigation()
}
