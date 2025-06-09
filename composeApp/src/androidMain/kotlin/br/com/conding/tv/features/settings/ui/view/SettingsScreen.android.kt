package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes

@Composable
internal actual fun SettingsScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit
) {
    MobileSettingsScreen(
        goToBackScreen = goToBackScreen,
        goToNextScreen = goToNextScreen,
        goToAlternativeRoutes = goToAlternativeRoutes
    )
}
