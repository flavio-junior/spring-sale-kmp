package br.com.conding.tv.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import br.com.conding.tv.theme.Themes

@Composable
fun ContentHorizontal(
    spaceBy: Dp = Themes.size.spaceSize0,
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = spaceBy),
        modifier = Modifier.wrapContentSize(align = Alignment.Center),
        verticalAlignment = Alignment.CenterVertically
    ) {
        this.content()
    }
}
