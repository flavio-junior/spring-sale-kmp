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
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.Themes
import org.jetbrains.compose.resources.DrawableResource
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.arrow_outward
import springsale.composeapp.generated.resources.draw
import springsale.composeapp.generated.resources.refresh

@Composable
fun EmptyList(
    modifier: Modifier = Modifier,
    title: String,
    mainIcon: DrawableResource = Res.drawable.draw,
    description: String,
    onClick: () -> Unit = {},
    refresh: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            icon = mainIcon,
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
            Title(title = title)
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
                modifier = modifier.onClickable(onClick = onClick)
            ) {
                Description(
                    description = description,
                    modifier = modifier.onClickable(onClick = onClick)
                )
                IconDefault(
                    icon = Res.drawable.arrow_outward,
                    onClick = onClick
                )
                IconDefault(
                    icon = Res.drawable.refresh,
                    onClick = refresh
                )
            }
        }
    }
}
