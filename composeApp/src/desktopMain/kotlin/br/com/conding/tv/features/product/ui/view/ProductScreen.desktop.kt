package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import br.com.conding.tv.features.home.factory.availableServices
import br.com.conding.tv.features.shared.BodyPage
import br.com.conding.tv.features.shared.Services
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.PRODUCTS

@Composable
actual fun ProductScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit
) {
    BodyPage(
        body = {
            Row {
                Services(
                    label = PRODUCTS,
                    options = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                ProductsTabs(goToAlternativeRoutes = goToAlternativeRoutes)
            }
        }
    )
}
