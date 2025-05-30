package br.com.conding.tv.components.ui

import androidx.compose.runtime.Composable
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.features.shared.CommonScreen

@Composable
actual fun ContentScreen(
    definitionsScreen: DefinitionsScreen,
    goToBackScreen: () -> Unit,
    extra: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    CommonScreen(
        definitionsScreen = definitionsScreen,
        goToBackScreen = goToBackScreen,
        extra = extra,
        content = content
    )
}
