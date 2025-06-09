package br.com.conding.tv.features.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.Header
import br.com.conding.tv.components.ui.MiniButton
import br.com.conding.tv.resources.scroll
import br.com.conding.tv.theme.Themes

@Composable
internal fun CommonScreen(
    definitionsScreen: DefinitionsScreen,
    goToBackScreen: () -> Unit = {},
    goToNextScreen: () -> Unit = {},
    extra: @Composable (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(horizontal = Themes.size.spaceSize16)
    ) {
        if (definitionsScreen.label != null) {
            Header(label = definitionsScreen.label, goToBackScreen = goToBackScreen)
        }
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxSize()
                .scroll(scroll = definitionsScreen.scroll)
                .wrapContentHeight(align = definitionsScreen.alignment),
            horizontalAlignment = definitionsScreen.horizontalAlignment,
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            extra(false)
            content()
        }
        if (definitionsScreen.enableMiniButton) {
            MiniButton(
                modifier = Modifier.align(alignment = Alignment.BottomEnd),
                onClick = goToNextScreen
            )
        }
    }
}
