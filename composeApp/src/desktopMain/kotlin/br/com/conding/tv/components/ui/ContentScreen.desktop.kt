package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes

@Composable
actual fun ContentScreen(
    definitionsScreen: DefinitionsScreen,
    goToBackScreen: () -> Unit,
    extra: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    spaceSize = Themes.size.spaceSize36,
                    width = Themes.size.spaceSize2,
                    color = Themes.colors.primary
                )
                .background(color = Themes.colors.background)
                .padding(all = Themes.size.spaceSize36)
                .width(width = Themes.size.spaceSize400)
                .height(height = Themes.size.spaceSize500),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
            ) {
                extra(true)
                content()
            }
        }
    }
}
