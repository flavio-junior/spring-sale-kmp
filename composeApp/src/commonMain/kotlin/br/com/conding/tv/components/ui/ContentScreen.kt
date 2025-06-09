package br.com.conding.tv.components.ui

import androidx.compose.runtime.Composable
import br.com.conding.tv.components.model.DefinitionsScreen

@Composable
internal expect fun ContentScreen(
    definitionsScreen: DefinitionsScreen = DefinitionsScreen(),
    goToBackScreen: () -> Unit = {},
    goToNextScreen: () -> Unit = {},
    extra: @Composable (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {}
)
