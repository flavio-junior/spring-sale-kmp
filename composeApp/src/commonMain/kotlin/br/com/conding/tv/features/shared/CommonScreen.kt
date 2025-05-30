package br.com.conding.tv.features.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.Header
import br.com.conding.tv.resources.scroll
import br.com.conding.tv.theme.Themes

@Composable
fun CommonScreen(
    definitionsScreen: DefinitionsScreen,
    goToBackScreen: () -> Unit,
    extra: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        if (definitionsScreen.label != null) {
            Header(label = definitionsScreen.label, goToBackScreen = goToBackScreen)
        }
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxSize()
                .padding(horizontal = Themes.size.spaceSize36)
                .scroll(scroll = definitionsScreen.scroll)
                .wrapContentHeight(align = definitionsScreen.alignment),
            horizontalAlignment = definitionsScreen.horizontalAlignment,
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            extra(false)
            content()
        }
    }
}
