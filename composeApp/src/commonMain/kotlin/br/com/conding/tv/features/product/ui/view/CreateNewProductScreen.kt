package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings

@Composable
internal fun CreateNewProductScreen(
    goToBackScreen: () -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            label = GenericsStrings.CREATE_PRODUCT,
            alignment = Alignment.Top,
            scroll = true
        ),
        goToBackScreen = goToBackScreen,
        content = {

        }
    )
}
