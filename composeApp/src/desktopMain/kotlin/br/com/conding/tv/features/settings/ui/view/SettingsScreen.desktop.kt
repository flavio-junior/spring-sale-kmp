package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.features.home.factory.availableServices
import br.com.conding.tv.features.shared.BodyPage
import br.com.conding.tv.features.shared.Services
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.ADJUSTMENTS

@Composable
actual fun SettingsScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit
) {
    BodyPage(
        body = {
            Services(
                label = ADJUSTMENTS,
                options = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    )
}
