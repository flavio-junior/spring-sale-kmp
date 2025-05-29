package br.com.conding.tv.features.account.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.Header
import br.com.conding.tv.theme.Themes

@Composable
actual fun ContentAccount(
    label: String?,
    goToBackScreen: () -> Unit,
    extra: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        if (label != null) {
            Header(label = label, goToBackScreen = goToBackScreen)
        }
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxSize()
                .padding(horizontal = Themes.size.spaceSize36)
                .verticalScroll(state = rememberScrollState())
                .wrapContentHeight(align = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            extra(false)
            content()
        }
    }
}
