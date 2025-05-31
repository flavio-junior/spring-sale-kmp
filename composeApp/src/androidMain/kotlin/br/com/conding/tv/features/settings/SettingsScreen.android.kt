package br.com.conding.tv.features.settings

import androidx.compose.runtime.Composable
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes

@Composable
actual fun SettingsScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit
) {
    MobileSettingsScreen(goToBackScreen, goToNextScreen, goToAlternativeRoutes)
}
