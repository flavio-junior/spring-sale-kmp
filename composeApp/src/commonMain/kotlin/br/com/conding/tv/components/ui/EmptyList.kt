package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.resources.GenericStrings.EMPTY_TEXT
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.Themes

@Composable
internal fun EmptyList(
    modifier: Modifier = Modifier,
    type: TypeSystem = TypeSystem.DESKTOP,
    title: String,
    iconName: IconName = IconName.DRAW,
    description: String? = null,
    onClick: () -> Unit = {},
    refresh: () -> Unit = {}
) {
    when (type) {
        TypeSystem.DESKTOP -> {
            DesktopEmptyList(
                modifier = modifier,
                title = title,
                iconName = iconName,
                description = description,
                onClick = onClick,
                refresh = refresh
            )
        }

        TypeSystem.MOBILE -> {
            MobileEmptyList(title = title)
        }
    }
}

@Composable
private fun MobileEmptyList(
    title: String,
) {
    Title(
        label = title,
        backgroundTransparent = true
    )
}

@Composable
private fun DesktopEmptyList(
    modifier: Modifier = Modifier,
    title: String,
    iconName: IconName = IconName.DRAW,
    description: String? = null,
    onClick: () -> Unit = {},
    refresh: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            iconName = iconName,
            size = Themes.size.spaceSize100
        )
        Spacer(
            modifier = modifier
                .background(color = Themes.colors.success)
                .width(width = Themes.size.spaceSize16)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(
                label = title,
                backgroundTransparent = true
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
                modifier = modifier.onClickable(onClick = onClick)
            ) {
                Description(
                    label = description ?: EMPTY_TEXT,
                    backgroundTransparent = true,
                    modifier = modifier.onClickable(onClick = onClick)
                )
                IconDefault(
                    iconName = IconName.ARROW_OUTWARD,
                    onClick = onClick
                )
                IconDefault(
                    iconName = IconName.REFRESH,
                    onClick = refresh
                )
            }
        }
    }
}
