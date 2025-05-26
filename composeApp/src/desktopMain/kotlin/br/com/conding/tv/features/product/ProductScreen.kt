package br.com.conding.tv.features.product

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import br.com.conding.tv.domain.factory.availableServices
import br.com.conding.tv.features.product.ui.view.ProductsTabs
import br.com.conding.tv.features.shared.BodyPage
import br.com.conding.tv.features.shared.Services
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.PRODUCTS

@Composable
fun ProductScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
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

