package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.conding.tv.getIconResource
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes
import androidx.compose.material3.Icon

@Composable
fun MiniButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    iconName: IconName = IconName.CREATE
) {
    Column(
        modifier = modifier
            .onBorder(
                onClick = { onClick() },
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .background(color = Themes.colors.secondary)
    ) {
        Icon(
            painter = getIconResource(iconName = iconName),
            contentDescription = iconName.name,
            modifier = Modifier
                .padding(all = Themes.size.spaceSize16)
                .size(size = Themes.size.spaceSize24),
            tint = Themes.colors.primary
        )
    }
}
