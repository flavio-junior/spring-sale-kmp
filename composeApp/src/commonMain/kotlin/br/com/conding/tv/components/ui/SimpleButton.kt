package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes

@Composable
internal fun SimpleButton(
    modifier: Modifier = Modifier,
    label: String,
    background: Color = Themes.colors.success,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .onBorder(
                onClick = onClick,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .background(color = background)
            .padding(all = Themes.size.spaceSize18)
            .fillMaxWidth()
    ) {
        Description(label = label)
    }
}
