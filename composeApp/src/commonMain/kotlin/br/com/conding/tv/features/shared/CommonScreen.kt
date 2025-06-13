package br.com.conding.tv.features.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
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
    content: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    val bottomBarHeight = if (definitionsScreen.enableBottomBar) {
        Themes.size.spaceSize64
    } else {
        Themes.size.spaceSize0
    }
    Scaffold(
        topBar = {
            if (definitionsScreen.label != null) {
                Header(
                    label = definitionsScreen.label,
                    goToBackScreen = goToBackScreen
                )
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = Themes.colors.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Themes.size.spaceSize16)
                        .padding(bottom = bottomBarHeight)
                        .scroll(scroll = definitionsScreen.scroll)
                        .wrapContentHeight(align = definitionsScreen.alignment),
                    horizontalAlignment = definitionsScreen.horizontalAlignment,
                    verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
                ) {
                    extra(false)
                    content()
                }
                if (definitionsScreen.enableBottomBar) {
                    Column(
                        modifier = Modifier.align(alignment = Alignment.BottomCenter),
                        verticalArrangement = Arrangement.Center
                    ) {
                        bottomBar()
                    }
                    if (definitionsScreen.enableMiniButton) {
                        MiniButton(
                            modifier = Modifier
                                .align(alignment = Alignment.BottomEnd)
                                .padding(end = Themes.size.spaceSize16),
                            onClick = goToNextScreen
                        )
                    }
                }
            }
        }
    )
}
