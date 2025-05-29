package br.com.conding.tv.features.settings

import androidx.compose.runtime.Composable
import br.com.conding.tv.features.home.factory.availableServices
import br.com.conding.tv.features.shared.BodyPage
import br.com.conding.tv.features.shared.Services
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.ADJUSTMENTS

@Composable
fun SettingsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
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
