package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.features.home.factory.availableServices
import br.com.conding.tv.features.shared.BodyPage
import br.com.conding.tv.features.shared.Services
import br.com.conding.tv.features.utils.TypeLayout
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings.ADJUSTMENTS

@Composable
internal actual fun SettingsScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (HttpError) -> Unit
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = ADJUSTMENTS,
                options = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
            DesktopEditProfileUserScreen()
        }
    )
}
